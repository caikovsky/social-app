package life.league.challenge.kotlin.data.repositories

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import life.league.challenge.kotlin.data.database.dao.PostDao
import life.league.challenge.kotlin.data.model.local.PostEntity
import life.league.challenge.kotlin.data.model.remote.AccountResponse
import life.league.challenge.kotlin.data.model.remote.UserResponse
import life.league.challenge.kotlin.domain.model.PostDomain
import life.league.challenge.kotlin.util.readFile
import life.league.challenge.kotlin.util.shouldBe
import okhttp3.mockwebserver.MockResponse
import org.junit.Test

@ExperimentalSerializationApi
class PostRepositoryTest : BaseRepositoryTest() {

    private val postDao = mockk<PostDao>()
    private val postRepository = PostRepositoryImpl(api = service, postDao = postDao)

    @Test
    fun `should hit posts endpoint on the API with an user id and return user's posts`() = runBlocking {
        val accountResponse = Json.decodeFromString<AccountResponse>(readFile("login_response.json"))
        val userResponse = Json.decodeFromString<List<UserResponse>>(readFile("users_response.json"))
        server.enqueue(MockResponse().setBody(readFile("posts_response.json")))

        coEvery { postDao.getAllPostsFromUser(any() as Int) } returns listOf()
        coEvery { postDao.insertPost(any() as PostEntity) } returns Unit

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

    @Test
    fun `should fetch posts from database with an user id and return the cached user's posts`() = runBlocking {
        val accountResponse = Json.decodeFromString<AccountResponse>(readFile("login_response.json"))
        val postEntity = PostEntity(
            userId = 1,
            id = 1,
            title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
            body = "quia et suscipit\n" +
                    "suscipit recusandae consequuntur expedita et cum\n" +
                    "reprehenderit molestiae ut ut quas totam\n" +
                    "nostrum rerum est autem sunt rem eveniet architecto"
        )

        coEvery { postDao.getAllPostsFromUser(any() as Int) } returns listOf(postEntity)

        val actual = postRepository(accountResponse.apiKey, 1)

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