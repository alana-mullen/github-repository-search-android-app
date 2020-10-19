package co.uk.thewirelessguy.githubrepositorysearch.api

import co.uk.thewirelessguy.githubrepositorysearch.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApiClient {

    private const val BASE_URL = "https://api.github.com/"

    @Provides
    @Singleton
    fun getClient(): ApiInterface {

        // OkHttpClient for building http request url:
        val client = OkHttpClient.Builder()

        /**
         * Stetho Interceptor: Inspect network calls made by the app using Chrome Developer Tools.
         * Connect your phone, start the app, and navigate to chrome://inspect on your development machine.
         * https://github.com/facebook/stetho
         **/
        if (BuildConfig.DEBUG) client.addNetworkInterceptor(StethoInterceptor())

        val moshi = Moshi.Builder()
                // Add any additional adapters before this line:
                .add(KotlinJsonAdapterFactory())
                .build()

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(client.build())
                .build().create(ApiInterface::class.java)
    }

}