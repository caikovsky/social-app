package life.league.challenge.kotlin.data.repositories

import android.util.Base64
import life.league.challenge.kotlin.data.api.Api
import life.league.challenge.kotlin.domain.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val api: Api) : LoginRepository {
    override suspend fun invoke(username: String, password: String) =
        api.login("Basic " + Base64.encodeToString("$username:$password".toByteArray(), Base64.NO_WRAP))
}