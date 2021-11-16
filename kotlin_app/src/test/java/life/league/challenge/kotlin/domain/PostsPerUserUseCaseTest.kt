package life.league.challenge.kotlin.domain

import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import life.league.challenge.kotlin.domain.repositories.LoginRepository
import life.league.challenge.kotlin.domain.repositories.PostRepository
import life.league.challenge.kotlin.domain.repositories.UserRepository
import life.league.challenge.kotlin.util.MainCoroutineRule
import life.league.challenge.kotlin.util.MockedModelGenerator
import life.league.challenge.kotlin.util.shouldBe
import org.junit.After
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PostsPerUserUseCaseTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val loginRepository: LoginRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private val postRepository: PostRepository = mockk()
    private val postsPerUserUseCase = PostsPerUserUseCase(loginRepository, userRepository, postRepository)

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `return a list of posts per user successfully`() {
        val name = "name"
        val password = "password"
        val token = MockedModelGenerator.getAccountDomain()
        val users = listOf(MockedModelGenerator.getUserDomain())
        val posts = listOf(MockedModelGenerator.getPostDomain())

        mainCoroutineRule.runBlockingTest {
            val expected = MockedModelGenerator.getPostPerUserDomainList()

            coEvery { loginRepository(name, password) } returns token
            coEvery { userRepository(token.apiKey) } returns users
            coEvery { postRepository(token.apiKey, any() as Int) } returns posts

            val result = postsPerUserUseCase(name, password)

            expected shouldBe result
        }
    }

}