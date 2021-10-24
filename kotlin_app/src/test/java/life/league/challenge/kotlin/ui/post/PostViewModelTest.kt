package life.league.challenge.kotlin.ui.post

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import life.league.challenge.kotlin.domain.PostsPerUserUseCase
import life.league.challenge.kotlin.domain.model.toViewEntity
import life.league.challenge.kotlin.util.MainCoroutineRule
import life.league.challenge.kotlin.util.MockedModelGenerator
import life.league.challenge.kotlin.util.shouldBe
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
class PostViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: PostViewModel
    private val postsPerUserUseCase: PostsPerUserUseCase = mockk()

    @Before
    fun setUp() {
        viewModel = PostViewModel(postsPerUserUseCase)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @ExperimentalTime
    @Test
    fun `loading as default value`() {
        mainCoroutineRule.runBlockingTest {
            viewModel.state.test {
                awaitItem() shouldBe PostViewModel.State.Loading
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `successfully retrieve the posts per user when initilize event is triggered`() {
        mainCoroutineRule.runBlockingTest {
            val expected = MockedModelGenerator.getPostPerUserDomainList().map {
                it.toViewEntity()
            }

            coEvery { postsPerUserUseCase(any() as String, any() as String) } returns MockedModelGenerator.getPostPerUserDomainList()
            viewModel.onEvent(PostViewModel.UiEvent.Initialize)

            viewModel.state.test {
                awaitItem() shouldBe PostViewModel.State.Content(expected)
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `successfully retrieve the posts per user when refresh event is triggered`() {
        mainCoroutineRule.runBlockingTest {
            val expected = MockedModelGenerator.getPostPerUserDomainList().map {
                it.toViewEntity()
            }

            coEvery { postsPerUserUseCase(any() as String, any() as String) } returns MockedModelGenerator.getPostPerUserDomainList()
            viewModel.onEvent(PostViewModel.UiEvent.Refresh)

            viewModel.state.test {
                awaitItem() shouldBe PostViewModel.State.Content(expected)
            }
        }
    }

    @ExperimentalTime
    @Test
    fun `error state when something goes wrong`() {
        mainCoroutineRule.runBlockingTest {
            coEvery { postsPerUserUseCase(any() as String, any() as String) } throws Exception()

            viewModel.onEvent(PostViewModel.UiEvent.Initialize)

            viewModel.state.test {
                awaitItem() shouldBe PostViewModel.State.Error
            }
        }
    }

}