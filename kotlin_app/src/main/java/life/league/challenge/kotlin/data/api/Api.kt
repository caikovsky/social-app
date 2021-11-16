package life.league.challenge.kotlin.data.api

import life.league.challenge.kotlin.data.model.remote.AccountResponse
import life.league.challenge.kotlin.data.model.remote.PostResponse
import life.league.challenge.kotlin.data.model.remote.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

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