package life.league.challenge.kotlin.ui.post

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.data.api.Service
import life.league.challenge.kotlin.data.api.getUsers
import life.league.challenge.kotlin.data.api.login
import life.league.challenge.kotlin.ui.model.User
import life.league.challenge.kotlin.ui.model.toUiModel
import life.league.challenge.kotlin.util.logE
import life.league.challenge.kotlin.util.logV

class PostViewModel : ViewModel() {

    private var _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    init {
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
        viewModelScope.launch {
            val users = Service.api.getUsers(apiKey)

            _users.value = users.map { user ->
                user.toUiModel()
            }
        }

    }
}

class PostViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = modelClass.cast(PostViewModel())!!
}