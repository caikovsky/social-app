package life.league.challenge.kotlin.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.domain.PostsPerUserUseCase
import life.league.challenge.kotlin.ui.model.Post
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postsPerUserUseCase: PostsPerUserUseCase) : ViewModel() {

    sealed class State {
        object Loading : State()
        object Error : State()
        data class Content(val posts: List<Post>) : State()
    }

    sealed class UiEvent {
        object Initialize : UiEvent()
        object Refresh : UiEvent()
    }

    private var _stateLiveData = MutableLiveData<State>()
    val stateLiveData: LiveData<State> get() = _stateLiveData

    fun onEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.Initialize, UiEvent.Refresh -> {
                _stateLiveData.value = State.Loading

                viewModelScope.launch {
                    val posts = getPostsPerUser()
                    _stateLiveData.value = State.Content(posts)
                }
            }
        }
    }

    private suspend fun getPostsPerUser(): List<Post> {
        return postsPerUserUseCase("", "").map {
            Post(
                userId = it.userId,
                name = it.name,
                thumbnail = it.thumbnail,
                title = it.posts.last().title,
                body = it.posts.last().body
            )
        }
    }
}
