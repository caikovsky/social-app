package life.league.challenge.kotlin.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        viewModel.onEvent(UiEvent.Initialize)
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
            viewModel.stateLiveData.collect { state ->
                when (state) {
                    is State.Loading -> showProgressDialog()
                    is State.Error -> hideProgressDialog()
                    is State.Content -> {
                        hideProgressDialog()
                        postAdapter.submitList(state.posts)
                    }
                }
            }
        }
    }

    private fun showProgressDialog() {
        binding.loading.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

    private fun hideProgressDialog() {
        binding.loading.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView() {
        with(binding.recyclerView) {
            setHasFixedSize(true)
            adapter = postAdapter
        }
    }
}