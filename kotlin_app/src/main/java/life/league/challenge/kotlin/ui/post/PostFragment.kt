package life.league.challenge.kotlin.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import life.league.challenge.kotlin.databinding.FragmentPostBinding

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
    }

    private fun setListeners() {
        with(binding) {
            swipeLayout.setOnRefreshListener {
                viewModel.getPostsPerUser()
                binding.swipeLayout.isRefreshing = false
            }
        }
    }

    private fun setUpObservers() {
        viewModel.posts.observe(viewLifecycleOwner, { posts ->
            postAdapter.submitList(posts)
            binding.swipeLayout.isRefreshing = false
        })

        viewModel.loading.observe(viewLifecycleOwner, { show ->
            if (show) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        })
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