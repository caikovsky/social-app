package life.league.challenge.kotlin.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import life.league.challenge.kotlin.R
import life.league.challenge.kotlin.data.api.Service
import life.league.challenge.kotlin.data.api.login
import life.league.challenge.kotlin.util.logE
import life.league.challenge.kotlin.util.logV

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        // example api call to login, feel free to delete this and implement the call to login
        // somewhere else differently depending on your chosen architecture
        lifecycleScope.launch(Dispatchers.IO) {
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
        logV(users.toString() ?: "")
    }

}
