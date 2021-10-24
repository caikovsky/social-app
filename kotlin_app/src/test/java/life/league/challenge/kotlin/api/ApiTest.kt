package life.league.challenge.kotlin.api

import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import life.league.challenge.kotlin.data.api.Api
import life.league.challenge.kotlin.data.model.AccountResponse
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiTest {

    // TODO: Use the same retrofit of the production code
    @Test
    fun `should hit login endpoint with expected parameters`() = runBlocking {
        val server = MockWebServer()
        server.start()
        server.enqueue(MockResponse().setBody(readFile("login_response.json")))

        val retrofit = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: Api = retrofit.create(Api::class.java)
        val result = service.login("")
        val expectedResult = Gson().parseFrom<AccountResponse>("login_response")
        val request = server.takeRequest()
        server.shutdown()

        assertThat(request.path, `is`("/login"))
        assertThat(result, `is`(expectedResult))
    }

}