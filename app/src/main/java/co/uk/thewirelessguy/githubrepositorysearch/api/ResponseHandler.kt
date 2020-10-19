package co.uk.thewirelessguy.githubrepositorysearch.api

import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1),
    NoInternet(-2),
    JsonDataError(-3)
}

open class ResponseHandler {
    fun <T : Any> handleSuccess(data: T): State<T> {
        return State.success(data)
    }

    fun <T : Any> handleException(e: Throwable): State<T> {
        Timber.d("handleException: $e")
        return when (e) {
            is HttpException -> State.error(getErrorMessage(e.code()), null)
            is SocketTimeoutException -> State.error(
                getErrorMessage(ErrorCodes.SocketTimeOut.code),
                null
            )
            is IOException -> State.error(getErrorMessage(ErrorCodes.NoInternet.code), null)
            is JsonDataException -> {
                Timber.d("Error: %s", e.message)
                State.error(getErrorMessage(-3), null)
            }
            else -> {
                Timber.d("Error: %s", e.message)
                State.error(getErrorMessage(Int.MAX_VALUE), null)
            }
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            ErrorCodes.NoInternet.code -> "No internet connection available"
            ErrorCodes.JsonDataError.code -> "Unable to parse data"
            401 -> "Unauthorised"
            404 -> "Not found"
            else -> "Something went wrong"
        }
    }
}