package life.league.challenge.kotlin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.data.api.Service
import life.league.challenge.kotlin.data.api.getUsers
import life.league.challenge.kotlin.data.api.login
import life.league.challenge.kotlin.util.logE
import life.league.challenge.kotlin.util.logV

class PostViewModel : ViewModel() {

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

    private suspend fun getUsers(apiKey: String) {
        val users = Service.api.getUsers(apiKey)
        logV(users.toString())
    }
}

class PostViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = modelClass.cast(PostViewModel())!!
}