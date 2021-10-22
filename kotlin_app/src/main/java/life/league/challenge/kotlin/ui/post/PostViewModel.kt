package life.league.challenge.kotlin.ui.post

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.data.api.Service
import life.league.challenge.kotlin.data.api.getPosts
import life.league.challenge.kotlin.data.api.getUsers
import life.league.challenge.kotlin.data.api.login
import life.league.challenge.kotlin.ui.model.Post
import life.league.challenge.kotlin.ui.model.User
import life.league.challenge.kotlin.ui.model.toUiModel
import life.league.challenge.kotlin.util.logE

class PostViewModel : ViewModel() {

    private var _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> get() = _posts

    init {
        _posts.value = listOf()

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                Service.api.login("hello", "world")
            }.onSuccess { response ->
                response.apiKey?.let { key -> getUsers(key) }
            }.onFailure { t ->
                logE(t)
            }
        }
    }

    private fun getUsers(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                Service.api.getUsers(apiKey)
            }.onSuccess { users ->
                users.forEach { user -> getPosts(apiKey, user.toUiModel()) }
            }.onFailure { t ->
                logE(t)
            }
        }
    }

    private fun getPosts(apiKey: String, user: User) {
        viewModelScope.launch {
            runCatching {
                Service.api.getPosts(apiKey, user.id)
            }.onSuccess { posts ->
                val lastPost = posts.last()
                val post = Post(user, lastPost.id, lastPost.title, lastPost.body)
                _posts.value = _posts.value?.plus(post)
            }.onFailure { throwable ->
                logE(throwable)
            }
        }
    }
}

class PostViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = modelClass.cast(PostViewModel())!!
}