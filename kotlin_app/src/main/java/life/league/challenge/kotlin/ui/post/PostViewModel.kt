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
class PostViewModel @Inject constructor(private val postsPerUserUseCase: PostsPerUserUseCase) : ViewModel() {

    private var _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    fun onEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.Initialize, UiEvent.Refresh -> getPostsPerUser()
        }
    }

    private fun getPostsPerUser() = viewModelScope.launch {
        _state.emit(State.Loading)

        runCatching { postsPerUserUseCase().toViewEntities() }
            .onSuccess { posts ->
                _state.emit(State.Content(posts))
            }.onFailure { throwable ->
                Log.e(this::class.simpleName, "onEvent: ${throwable.message}")
                _state.emit(State.Error)
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

    sealed class State {
        object Loading : State()
        object Error : State()
        data class Content(val posts: List<Post>) : State()
    }

    sealed class UiEvent {
        object Initialize : UiEvent()
        object Refresh : UiEvent()
    }
}
