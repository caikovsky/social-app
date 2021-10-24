package life.league.challenge.kotlin.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.domain.PostsPerUserUseCase
import life.league.challenge.kotlin.domain.model.toViewEntity
import life.league.challenge.kotlin.ui.model.Post
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postsPerUserUseCase: PostsPerUserUseCase) : ViewModel() {

    private var _state = MutableStateFlow<State>(State.Loading)
    val state: StateFlow<State> get() = _state

    fun onEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.Initialize, UiEvent.Refresh -> {
                viewModelScope.launch {
                    _state.emit(State.Loading)

                    try {
                        val posts = getPostsPerUser()
                        _state.emit(State.Content(posts))
                    } catch (e: Exception) {
                        _state.emit(State.Error)
                    }
                }
            }
        }
    }

    private suspend fun getPostsPerUser(): List<Post> {
        return postsPerUserUseCase("", "").map {
            it.toViewEntity()
        }
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
