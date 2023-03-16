package life.league.challenge.kotlin.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import life.league.challenge.kotlin.ui.post.PostViewModel.UiEvent

@AndroidEntryPoint
class PostFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (savedInstanceState == null) viewModel.onEvent(UiEvent.Initialize)

        return ComposeView(requireContext()).apply {
            setContent {
                val state by viewModel.state.collectAsState()

                PostScreen(state = state, onRefresh = { viewModel.onEvent(UiEvent.Refresh) })
            }
        }
    }
}
