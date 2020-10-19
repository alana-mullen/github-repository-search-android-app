package co.uk.thewirelessguy.githubrepositorysearch.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.uk.thewirelessguy.githubrepositorysearch.api.State
import co.uk.thewirelessguy.githubrepositorysearch.model.Repository
import co.uk.thewirelessguy.githubrepositorysearch.repository.RepoDetailRepository
import kotlinx.coroutines.launch

class RepoDetailViewModel @ViewModelInject constructor(
    private val repository: RepoDetailRepository
) : ViewModel() {

    // Encapsulate access to mutable LiveData using backing property:
    private val _repoDetailLiveData = MutableLiveData<State<Repository>>()
    val repoDetailLiveData: LiveData<State<Repository>> = _repoDetailLiveData

    fun getRepoDetailLiveData(user: String, repo: String) {
        viewModelScope.launch {
            val repoDetail = repository.fetchRepoDetail(user, repo)
            _repoDetailLiveData.postValue(repoDetail)
        }
    }


    // Encapsulate access to mutable LiveData using backing property:
    private val _repoReadmeLiveData = MutableLiveData<State<String>>()
    val repoReadmeLiveData: LiveData<State<String>> = _repoReadmeLiveData

    fun getRepoReadmeLiveData(user: String, repo: String) {
        viewModelScope.launch {
            val repoReadme = repository.fetchRepoReadme(user, repo)
            _repoReadmeLiveData.postValue(repoReadme)
        }
    }
}
