package life.league.challenge.kotlin.ui.post

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import life.league.challenge.kotlin.ui.components.ErrorMessage
import life.league.challenge.kotlin.ui.components.PostsList
import life.league.challenge.kotlin.ui.components.ProgressBar


@Composable
fun PostScreen(postViewModel: PostViewModel = hiltViewModel(), navController: NavController) {
    postViewModel.onEvent(PostViewModel.UiEvent.Initialize)
    val state = postViewModel.uiState.value

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            TopAppBar(title = { Text("Mobile Coding Challenge") }
            )
        }) {
        if (state.isLoading) ProgressBar()
        if (state.posts.isNotEmpty() && state.errorMessage.isNullOrEmpty()) PostsList(posts = state.posts)
        if (!state.errorMessage.isNullOrEmpty()) ErrorMessage(message = state.errorMessage)
    }
}