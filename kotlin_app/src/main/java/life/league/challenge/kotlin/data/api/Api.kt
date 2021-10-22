package life.league.challenge.kotlin.data.api

import android.util.Base64
import life.league.challenge.kotlin.data.model.AccountResponse
import life.league.challenge.kotlin.data.model.PostResponse
import life.league.challenge.kotlin.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Retrofit API interface definition using coroutines. Feel free to change this implementation to
 * suit your chosen architecture pattern and concurrency tools
 */
interface Api {

    @GET("login")
    suspend fun login(@Header("Authorization") credentials: String?): AccountResponse

    @GET("users")
    suspend fun users(@Header("x-access-token") accessToken: String): List<UserResponse>

    @GET("posts")
    suspend fun posts(
        @Header("x-access-token") accessToken: String,
        @Query("userId") userId: Int
    ): List<PostResponse>

}

/**
 * Overloaded Login API extension function to handle authorization header encoding
 */
suspend fun Api.login(username: String, password: String) =
    login("Basic " + Base64.encodeToString("$username:$password".toByteArray(), Base64.NO_WRAP))

suspend fun Api.getUsers(apiKey: String) = users(accessToken = apiKey)

suspend fun Api.getPosts(apiKey: String, userId: Int) = posts(accessToken = apiKey, userId = userId)