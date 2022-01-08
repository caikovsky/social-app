package life.league.challenge.kotlin.ui.post

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import life.league.challenge.kotlin.ui.components.ErrorMessage
import life.league.challenge.kotlin.ui.components.PostsList
import life.league.challenge.kotlin.ui.components.ProgressBar


@Composable
fun PostScreen(postViewModel: PostViewModel = hiltViewModel(), navController: NavController) {
    postViewModel.onEvent(UiEvent.Initialize)
    val state by postViewModel.uiState.collectAsState()

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            TopAppBar(title = { Text("Mobile Coding Challenge") }
            )
        }) {
        when (state) {
            is UiState.Loading -> ProgressBar()
            is UiState.Error -> ErrorMessage()
            is UiState.Success -> PostsList(posts = (state as UiState.Success).posts)
        }
    }
}