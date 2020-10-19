package co.uk.thewirelessguy.githubrepositorysearch.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.uk.thewirelessguy.githubrepositorysearch.api.State
import co.uk.thewirelessguy.githubrepositorysearch.model.Repositories
import co.uk.thewirelessguy.githubrepositorysearch.repository.QueryResultsRepository
import kotlinx.coroutines.launch

class QueryResultsViewModel @ViewModelInject constructor(
    private val repository: QueryResultsRepository
) : ViewModel() {

    // Encapsulate access to mutable LiveData using backing property:
    private val _repositoryQueryLiveData = MutableLiveData<State<Repositories>>()
    val repositoryQueryLiveData: LiveData<State<Repositories>> = _repositoryQueryLiveData

    fun getRepositoryQueryLiveData(searchQuery: String) {
        viewModelScope.launch {
            val searchResults = repository.fetchRepositoryQueryResults(searchQuery)
            _repositoryQueryLiveData.postValue(searchResults)
        }
    }
}
