package co.uk.thewirelessguy.githubrepositorysearch.api

import co.uk.thewirelessguy.githubrepositorysearch.model.Repositories
import co.uk.thewirelessguy.githubrepositorysearch.model.Repository
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiInterface {
    @GET("search/repositories")
    suspend fun getRepositoryList(@Query("q") searchQuery: String): Repositories

    @GET("repos/{user}/{repo}")
    suspend fun getRepoDetail(@Path("user") user: String, @Path("repo") repo: String): Repository

    // Get README.md as raw instead of JSON
    @Headers("Accept: application/vnd.github.V3.raw")
    @GET("repos/{user}/{repo}/readme")
    suspend fun getRepoReadme(@Path("user") user: String, @Path("repo") repo: String): ResponseBody

}