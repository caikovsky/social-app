package life.league.challenge.kotlin.ui.post

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.domain.PostsPerUserUseCase
import life.league.challenge.kotlin.domain.model.PostPerUserDomain
import life.league.challenge.kotlin.ui.model.Post
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postsPerUserUseCase: PostsPerUserUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun onEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.Initialize, UiEvent.Refresh -> getPostsPerUser()
        }
    }

    private fun getPostsPerUser() = viewModelScope.launch {
        _uiState.emit(UiState.Loading)

        runCatching { postsPerUserUseCase().toViewEntities() }
            .onSuccess { posts ->
                _uiState.emit(UiState.Success(posts = posts))
            }.onFailure { throwable ->
                Log.e(this::class.simpleName, "onEvent: ${throwable.message}")
                _uiState.emit(UiState.Error)
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
}

sealed class UiState {
    object Loading : UiState()
    object Error : UiState()
    data class Success(val posts: List<Post>) : UiState()
}

sealed class UiEvent {
    object Initialize : UiEvent()
    object Refresh : UiEvent()
}
