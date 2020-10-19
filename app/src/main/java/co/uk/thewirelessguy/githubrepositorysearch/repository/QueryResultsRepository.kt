package co.uk.thewirelessguy.githubrepositorysearch.repository

import co.uk.thewirelessguy.githubrepositorysearch.api.ApiInterface
import co.uk.thewirelessguy.githubrepositorysearch.api.ResponseHandler
import co.uk.thewirelessguy.githubrepositorysearch.api.State
import co.uk.thewirelessguy.githubrepositorysearch.model.Repositories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QueryResultsRepository @Inject constructor(
    private val client: ApiInterface
) {

    suspend fun fetchRepositoryQueryResults(searchQuery: String) : State<Repositories> {
        val responseHandler = ResponseHandler()

        return withContext(Dispatchers.IO) {
            try {
                val response = client.getRepositoryList(searchQuery)
                return@withContext responseHandler.handleSuccess(response)
            } catch (e: Exception) {
                return@withContext responseHandler.handleException(e)
            }

        }
    }

}