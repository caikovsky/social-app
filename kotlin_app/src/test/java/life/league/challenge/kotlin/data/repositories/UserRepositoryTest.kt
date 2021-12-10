package life.league.challenge.kotlin.data.repositories

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import life.league.challenge.kotlin.data.api.Api
import life.league.challenge.kotlin.data.database.dao.UserDao
import life.league.challenge.kotlin.data.model.local.UserEntity
import life.league.challenge.kotlin.data.model.remote.AccountResponse
import life.league.challenge.kotlin.di.NetworkModule
import life.league.challenge.kotlin.domain.model.UserDomain
import life.league.challenge.kotlin.util.readFile
import life.league.challenge.kotlin.util.shouldBe
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Ignore
import org.junit.Test


@ExperimentalSerializationApi
class UserRepositoryTest {

    private val server = MockWebServer()
    private val service: Api = NetworkModule.provideRetrofit(NetworkModule.provideOkHttpClient(), server.url(""))
    private val userDao = mockk<UserDao>()
    private val userRepository = UserRepositoryImpl(api = service, userDao = userDao)

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `should hit users endpoint on the API with an apikey and return the list of users`() = runBlocking {
        val accessToken = readFile("login_response.json")
        val accountResponse = Json.Default.decodeFromString<AccountResponse>(accessToken)
        server.enqueue(MockResponse().setBody(readFile("users_response.json")))

        coEvery { userDao.getAllUsers() } returns listOf()
        coEvery { userDao.insertUser(any() as UserEntity) } returns Unit

        val actual = userRepository(accountResponse.apiKey)
        val expected = UserDomain(
            id = 1,
            name = "Leanne Graham",
            thumbnail = "https://randomuser.me/api/portraits/thumb/men/84.jpg"
        )

        expected shouldBe actual.first()
    }

    @Test
    @Ignore
    fun `should fetch users from local database with an apikey and return the cached list of users`() = runBlocking {
        val accessToken = readFile("login_response.json")
        val accountResponse = Json.Default.decodeFromString<AccountResponse>(accessToken)
        val userEntity = UserEntity(
            id = 1,
            name = "Leanne Graham",
            username = "Bret",
            email = "Sincere@april.biz",
            phone = "1-770-736-8031 x56442",
            website = "hildegard.org",
            avatar = "https://randomuser.me/api/portraits/thumb/men/84.jpg"
        )

        coEvery { userDao.getAllUsers() } returns listOf(userEntity)

        val actual = userRepository(accountResponse.apiKey)
        val expected = UserDomain(
            id = 1,
            name = "Leanne Graham",
            thumbnail = "https://randomuser.me/api/portraits/thumb/men/84.jpg"
        )

        expected shouldBe actual.first()
    }
}