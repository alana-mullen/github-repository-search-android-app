package co.uk.thewirelessguy.githubrepositorysearch.repository

import co.uk.thewirelessguy.githubrepositorysearch.api.ApiInterface
import co.uk.thewirelessguy.githubrepositorysearch.api.ResponseHandler
import co.uk.thewirelessguy.githubrepositorysearch.api.State
import co.uk.thewirelessguy.githubrepositorysearch.model.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepoDetailRepository @Inject constructor(
    private val client: ApiInterface
) {

    suspend fun fetchRepoDetail(user: String, repo: String) : State<Repository> {
        val responseHandler = ResponseHandler()

        return withContext(Dispatchers.IO) {
            try {
                val response = client.getRepoDetail(user, repo)
                return@withContext responseHandler.handleSuccess(response)
            } catch (e: Exception) {
                return@withContext responseHandler.handleException(e)
            }
        }
    }

    suspend fun fetchRepoReadme(user: String, repo: String) : State<String> {
        val responseHandler = ResponseHandler()

        return withContext(Dispatchers.IO) {
            try {
                val response = client.getRepoReadme(user, repo)
                return@withContext responseHandler.handleSuccess(response.string())
            } catch (e: Exception) {
                return@withContext responseHandler.handleException(e)
            }
        }

    }

}