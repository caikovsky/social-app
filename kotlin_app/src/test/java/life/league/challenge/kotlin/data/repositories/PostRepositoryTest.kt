package life.league.challenge.kotlin.data.repositories

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import life.league.challenge.kotlin.data.api.Api
import life.league.challenge.kotlin.data.model.remote.AccountResponse
import life.league.challenge.kotlin.data.model.remote.UserResponse
import life.league.challenge.kotlin.domain.model.PostDomain
import life.league.challenge.kotlin.util.readFile
import life.league.challenge.kotlin.util.shouldBe
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit

class PostRepositoryTest {
    private val server = MockWebServer()
    private val service = Retrofit.Builder()
        .baseUrl(server.url(""))
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .client(OkHttpClient.Builder().build())
        .build()
        .create(Api::class.java)
    private val postRepository = PostRepositoryImpl(service)

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `should hit posts endpoint on the API with an user id and return user's posts`() = runBlocking {
        val accountResponse = Json.decodeFromString<AccountResponse>(readFile("login_response.json"))
        val userResponse = Json.decodeFromString<List<UserResponse>>(readFile("users_response.json"))
        server.enqueue(MockResponse().setBody(readFile("posts_response.json")))

        val actual = postRepository(accountResponse.apiKey, userResponse.first().id)

        val expected =
            PostDomain(
                id = 1,
                title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                body = "quia et suscipit\n" +
                        "suscipit recusandae consequuntur expedita et cum\n" +
                        "reprehenderit molestiae ut ut quas totam\n" +
                        "nostrum rerum est autem sunt rem eveniet architecto"
            )

        expected shouldBe actual.first()
    }
}