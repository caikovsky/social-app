package life.league.challenge.kotlin.data.repositories

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import life.league.challenge.kotlin.data.api.Api
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import retrofit2.Retrofit

@ExperimentalSerializationApi
open class BaseRepositoryTest {
    val server = MockWebServer()
    val service: Api = Retrofit.Builder()
        .baseUrl(server.url(""))
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .client(OkHttpClient.Builder().build())
        .build()
        .create(Api::class.java)

    @After
    fun tearDown() {
        server.shutdown()
    }
}