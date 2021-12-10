package life.league.challenge.kotlin.data.repositories

import android.util.Base64
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import life.league.challenge.kotlin.data.api.Api
import life.league.challenge.kotlin.di.NetworkModule
import life.league.challenge.kotlin.domain.model.AccountDomain
import life.league.challenge.kotlin.util.readFile
import life.league.challenge.kotlin.util.shouldBe
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalSerializationApi
class LoginRepositoryTest {

    private val server = MockWebServer()
    private val service: Api = NetworkModule.provideRetrofit(NetworkModule.provideOkHttpClient(), server.url(""))
    private val loginRepository = LoginRepositoryImpl(service)

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `should hit login endpoint on the API with any username or password and return a apiKey`() = runBlocking {
        val authorization = "Authorization encodedBase64"
        server.enqueue(MockResponse().setBody(readFile("login_response.json")))
        mockkStatic(Base64::class)

        every { Base64.encodeToString(any() as ByteArray, any() as Int) } returns authorization

        val actual = loginRepository("", "")
        val expected = AccountDomain(apiKey = "FC5E038D38A57032085441E7FE7010B0")

        expected shouldBe actual
    }
}