package life.league.challenge.kotlin.data.api

import android.util.Base64
import life.league.challenge.kotlin.data.model.AccountResponse
import life.league.challenge.kotlin.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * Retrofit API interface definition using coroutines. Feel free to change this implementation to
 * suit your chosen architecture pattern and concurrency tools
 */
interface Api {

    @GET("login")
    suspend fun login(@Header("Authorization") credentials: String?): AccountResponse

    @GET("users")
    suspend fun getUsers(@Header("x-access-token") apiKey: String): List<UserResponse>

}

/**
 * Overloaded Login API extension function to handle authorization header encoding
 */
suspend fun Api.login(username: String, password: String) =
    login("Basic " + Base64.encodeToString("$username:$password".toByteArray(), Base64.NO_WRAP))

suspend fun Api.getUsers(apiKey: String) = getUsers(apiKey)