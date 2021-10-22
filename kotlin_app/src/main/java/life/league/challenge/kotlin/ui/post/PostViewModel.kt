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
import life.league.challenge.kotlin.util.logV

class PostViewModel : ViewModel() {

    private var _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> get() = _posts

    init {
        _posts.value = listOf()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val account = Service.api.login("hello", "world")
                logV(account.apiKey ?: "")
                account.apiKey?.let { getUsers(it) }
            } catch (t: Throwable) {
                logE(t)
            }
        }
    }

    private fun getUsers(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val users = Service.api.getUsers(apiKey)
            users.forEach { user -> getPosts(apiKey, user.toUiModel()) }
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