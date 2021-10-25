package life.league.challenge.kotlin.data.repositories

import android.util.Base64
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import life.league.challenge.kotlin.data.api.Api
import life.league.challenge.kotlin.data.model.AccountResponse
import life.league.challenge.kotlin.domain.model.AccountDomain
import life.league.challenge.kotlin.domain.model.UserDomain
import life.league.challenge.kotlin.util.readFile
import life.league.challenge.kotlin.util.shouldBe
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit

class UserRepositoryTest {
    private val server = MockWebServer()
    private val service = Retrofit.Builder()
        .baseUrl(server.url(""))
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .client(OkHttpClient.Builder().build())
        .build()
        .create(Api::class.java)
    private val userRepository = UserRepositoryImpl(service)

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `should hit users endpoint on the API with an apikey and return the list of users`() = runBlocking {
        val accessToken = readFile("login_response.json")
        val accountResponse = Json.Default.decodeFromString<AccountResponse>(accessToken)
        server.enqueue(MockResponse().setBody(readFile("users_response.json")))

        val actual = userRepository(accountResponse.apiKey)
        val expected = UserDomain(id = 1, name = "Leanne Graham", thumbnail = "https://randomuser.me/api/portraits/thumb/men/84.jpg")

        expected shouldBe actual.first()
    }
}