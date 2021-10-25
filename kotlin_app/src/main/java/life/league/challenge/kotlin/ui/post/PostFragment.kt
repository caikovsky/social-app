package life.league.challenge.kotlin.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import life.league.challenge.kotlin.databinding.FragmentPostBinding
import life.league.challenge.kotlin.ui.post.PostViewModel.State
import life.league.challenge.kotlin.ui.post.PostViewModel.UiEvent

@AndroidEntryPoint
class PostFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding
    private val viewModel: PostViewModel by viewModels()
    private val postAdapter = PostAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setListeners()
        setUpObservers()

        if (savedInstanceState == null) viewModel.onEvent(UiEvent.Initialize)
    }

    private fun setListeners() {
        with(binding) {
            swipeLayout.setOnRefreshListener {
                viewModel.onEvent(UiEvent.Refresh)
                binding.swipeLayout.isRefreshing = false
            }
        }
    }

    private fun setUpObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    is State.Loading -> setVisibility(isLoadingShown = true)
                    is State.Error -> setVisibility(isErrorShown = true)
                    is State.Content -> handleContent(state)
                }
            }
        }
    }

    private fun handleContent(state: State.Content) {
        setVisibility(isContentShown = true)
        postAdapter.submitList(state.posts)
    }

    private fun setUpRecyclerView() {
        with(binding.recyclerView) {
            setHasFixedSize(true)
            adapter = postAdapter
        }
    }

    private fun setVisibility(
        isContentShown: Boolean = false,
        isErrorShown: Boolean = false,
        isLoadingShown: Boolean = false,
    ) {
        binding.recyclerView.isVisible = isContentShown
        binding.loading.isVisible = isLoadingShown
//        binding.error.isVisible = isErrorShown
    }
}