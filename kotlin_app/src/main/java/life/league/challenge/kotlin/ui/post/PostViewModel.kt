package life.league.challenge.kotlin.ui.post

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.domain.PostsPerUserUseCase
import life.league.challenge.kotlin.domain.model.PostPerUserDomain
import life.league.challenge.kotlin.ui.model.Post
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postsPerUserUseCase: PostsPerUserUseCase) : ViewModel() {

    private var _uiState = mutableStateOf(UiState())
    val uiState: State<UiState> = _uiState

    fun onEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.Initialize, UiEvent.Refresh -> getPostsPerUser()
        }
    }

    private fun getPostsPerUser() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isLoading = true)

        runCatching { postsPerUserUseCase().toViewEntities() }
            .onSuccess { posts ->
                _uiState.value = _uiState.value.copy(isLoading = false, posts = posts)
            }.onFailure { throwable ->
                Log.e(this::class.simpleName, "onEvent: ${throwable.message}")
                _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = throwable.message)
            }
    }

    private fun List<PostPerUserDomain>.toViewEntities() = map { domain ->
        Post(
            userId = domain.userId,
            name = domain.name,
            thumbnail = domain.thumbnail,
            title = domain.posts.last().title,
            body = domain.posts.last().body
        )
    }

    data class UiState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val posts: List<Post> = emptyList()
    )

    sealed class UiEvent {
        object Initialize : UiEvent()
        object Refresh : UiEvent()
    }
}
