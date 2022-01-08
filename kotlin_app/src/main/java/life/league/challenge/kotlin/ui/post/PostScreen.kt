package life.league.challenge.kotlin.ui.post

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.ui.components.ErrorMessage
import life.league.challenge.kotlin.ui.components.PostsList
import life.league.challenge.kotlin.ui.components.ProgressBar
import life.league.challenge.kotlin.ui.model.Post


@Composable
fun PostScreen(viewModel: PostViewModel = hiltViewModel(), navController: NavController) {
    viewModel.onEvent(UiEvent.Initialize)
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            TopAppBar(title = { Text(stringResource(id = R.string.app_name)) })
        }) {
        if (state.isLoading) ProgressBar()
        if (!state.errorMessage.isNullOrEmpty()) ErrorMessage(state.errorMessage!!)
        if (state.posts.isNotEmpty()) SwipeToRefreshLayout(
            posts = state.posts,
            isLoading = state.isLoading,
            viewModel = viewModel
        )
    }
}

@Composable
fun SwipeToRefreshLayout(posts: List<Post>, isLoading: Boolean, viewModel: PostViewModel) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isLoading),
        onRefresh = { viewModel.onEvent(UiEvent.Refresh) },
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = trigger,
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) {
        PostsList(posts = posts)
    }
}