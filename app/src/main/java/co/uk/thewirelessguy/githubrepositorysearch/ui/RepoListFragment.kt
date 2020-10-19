package co.uk.thewirelessguy.githubrepositorysearch.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.thewirelessguy.githubrepositorysearch.R
import co.uk.thewirelessguy.githubrepositorysearch.api.State
import co.uk.thewirelessguy.githubrepositorysearch.databinding.FragmentRepoListBinding
import co.uk.thewirelessguy.githubrepositorysearch.extension.setEmptyStateView
import co.uk.thewirelessguy.githubrepositorysearch.viewmodel.QueryResultsViewModel
import co.uk.thewirelessguy.githubrepositorysearch.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RepoListFragment : Fragment() {

    private var _binding: FragmentRepoListBinding? = null
    private val binding get() = _binding!!
    private lateinit var commitsAdapter: RepoListAdapter
    private val viewModel: QueryResultsViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRepoListBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        binding.repoList.apply {
            // Set LinearLayoutManager using default vertical list:
            layoutManager = LinearLayoutManager(context)

            hasFixedSize() // Improve performance (use only with fixed size items)
            setItemViewCacheSize(20)

            // Set adapter and initialise with empty list:
            commitsAdapter = RepoListAdapter(mutableListOf()) {
                // Navigate to RepoDetailFragment and pass author and repo name of clicked item
                val action = RepoListFragmentDirections.actionOpenRepoDetail(it.fullName.toString(), it.owner?.login.toString(), it.name.toString())
                val navController = Navigation.findNavController(binding.root)
                navController.navigate(action)
            }
            adapter = commitsAdapter

            // Set a view to display when list is empty:
            adapter?.setEmptyStateView(binding.repoListEmpty.root)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Listen for changes from the LiveData ViewModel. Update view when data is loaded.
        viewModel.repositoryQueryLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Success -> {
                    commitsAdapter.setData(it.data.items?.toMutableList())
                }
                is State.Error -> {
                    context?.toast("Error: ${it.message}")
                }
                is State.Loading -> showLoading()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_repo_list, menu)
        // EmptySubmitSearchView extends SearchView so that query submit gets triggered even when empty
        val searchView = EmptySubmitSearchView((activity as MainActivity).supportActionBar!!.themedContext)
        menu.findItem(R.id.action_search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Hide keyboard
                searchView.clearFocus()
                if (query.isNotEmpty()) {
                    loadData(query)
                } else {
                    // Clear RecyclerView list when search is empty
                    commitsAdapter.setData(arrayListOf())
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun loadData(searchQuery: String = "") {
        Timber.d("loadData")
        viewModel.getRepositoryQueryLiveData(searchQuery)
    }

    private fun showLoading() {
        Timber.d("Loading...")
    }

}