package life.league.challenge.kotlin.domain

import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import life.league.challenge.kotlin.data.model.PostResponse
import life.league.challenge.kotlin.data.model.UserResponse
import life.league.challenge.kotlin.util.MainCoroutineRule
import life.league.challenge.kotlin.util.MockedModelGenerator
import life.league.challenge.kotlin.util.shouldBe
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PostsPerUserUseCaseTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var postsPerUserUseCase: PostsPerUserUseCase
    private val loginRepository: LoginRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private val postRepository: PostRepository = mockk()

    @Before
    fun setUp() {
        postsPerUserUseCase = PostsPerUserUseCase(loginRepository, userRepository, postRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `return a list of posts per user successfully`() {
        val name = "name"
        val password = "password"
        val token = MockedModelGenerator.getAccountResponse()
        val users: List<UserResponse> = MockedModelGenerator.getUserResponseList()
        val posts: List<PostResponse> = MockedModelGenerator.getPostResponseList()

        mainCoroutineRule.runBlockingTest {
            val expected = MockedModelGenerator.getPostPerUserDomainList()

            coEvery { loginRepository(name, password) } returns token
            coEvery { userRepository(token.apiKey ?: "") } returns users
            coEvery { postRepository(token.apiKey ?: "", any() as Int) } returns posts

            val result = postsPerUserUseCase(name, password)

            expected shouldBe result
        }
    }

    @Test
    fun `login repository fails to create a token and returns null`() {
        val name = "name"
        val password = "password"
        val token = MockedModelGenerator.getAccountResponse(null)
        val users: List<UserResponse> = MockedModelGenerator.getUserResponseList()
        val posts: List<PostResponse> = MockedModelGenerator.getPostResponseList()

        runBlockingTest {
            val expected = MockedModelGenerator.getPostPerUserDomainList()

            coEvery { loginRepository(name, password) } returns token
            coEvery { userRepository(token.apiKey ?: "") } returns users
            coEvery { postRepository(token.apiKey ?: "", any() as Int) } returns posts

            val result = postsPerUserUseCase(name, password)

            expected shouldBe result
        }
    }

}