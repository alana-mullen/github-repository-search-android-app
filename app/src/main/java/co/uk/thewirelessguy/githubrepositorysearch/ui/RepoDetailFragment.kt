package co.uk.thewirelessguy.githubrepositorysearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import co.uk.thewirelessguy.githubrepositorysearch.api.State
import co.uk.thewirelessguy.githubrepositorysearch.databinding.FragmentRepoDetailBinding
import co.uk.thewirelessguy.githubrepositorysearch.viewmodel.RepoDetailViewModel
import co.uk.thewirelessguy.githubrepositorysearch.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepoDetailFragment : Fragment() {

    private lateinit var binding: FragmentRepoDetailBinding
    private val viewModel: RepoDetailViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRepoDetailBinding.inflate(
                inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Listen for changes from the LiveData ViewModel. Update view when data is loaded.
        viewModel.repoDetailLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Success -> {
                    binding.viewModel = it.data
                }
                is State.Error -> {
                    context?.toast("Error: ${it.message}")
                }
                is State.Loading -> {
                    //Todo: showLoading()
                }
            }
        }

        viewModel.repoReadmeLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Success -> {
                    // Convert markdown from README.md to spanned text
                    val markwon = Markwon.create(this.requireContext())
                    val spanned = markwon.toMarkdown(it.data)
                    binding.readme.text = spanned
                }
                is State.Error -> {
                    context?.toast("Error: ${it.message}")
                }
                is State.Loading -> {
                    //Todo: showLoading()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        arguments?.let {
            RepoDetailFragmentArgs.fromBundle(it).let { passedArguments ->
                lifecycleScope.launch {
                    viewModel.getRepoDetailLiveData(passedArguments.user, passedArguments.repo)
                    viewModel.getRepoReadmeLiveData(passedArguments.user, passedArguments.repo)
                }
            }

        }
    }

}