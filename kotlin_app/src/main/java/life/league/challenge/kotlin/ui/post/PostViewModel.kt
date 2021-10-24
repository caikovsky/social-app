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

    private var _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> get() = _posts

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        _posts.value = listOf()
        getPostsPerUser()
    }

    fun getPostsPerUser() {
        _loading.value = true

        viewModelScope.launch {
            val postsPerUser = postsPerUserUseCase("", "")
            _posts.value = postsPerUser.map {
                Post(
                    userId = it.userId,
                    name = it.name,
                    thumbnail = it.thumbnail,
                    title = it.posts.last().title,
                    body = it.posts.last().body
                )
            }

            _loading.value = false
        }
    }
}
