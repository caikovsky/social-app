package life.league.challenge.kotlin.ui.post

import android.util.Log
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import life.league.challenge.kotlin.domain.PostsPerUserUseCase
import life.league.challenge.kotlin.util.MainCoroutineRule
import life.league.challenge.kotlin.util.MockedModelGenerator
import life.league.challenge.kotlin.util.test
import org.junit.After
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
class PostViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val postsPerUserUseCase: PostsPerUserUseCase = mockk()
    private val viewModel = PostViewModel(postsPerUserUseCase)

    @After
    fun tearDown() {
        unmockkAll()
    }

    @ExperimentalTime
    @Test
    fun `successfully retrieve the posts per user when initialize event is triggered`() {
        mainCoroutineRule.runBlockingTest {
            val useCaseResponse = MockedModelGenerator.getPostPerUserDomainList()

            coEvery { postsPerUserUseCase(any() as String, any() as String) } returns useCaseResponse

            val expected = listOf(MockedModelGenerator.getPost())
            val observer = viewModel.state.test(this)

            viewModel.onEvent(PostViewModel.UiEvent.Initialize)

            observer.assertValues(PostViewModel.State.Loading, PostViewModel.State.Content(expected)).finish()
            observer.assertValueCount(2)
        }
    }

    @ExperimentalTime
    @Test
    fun `successfully retrieve the posts per user when refresh event is triggered`() {
        mainCoroutineRule.runBlockingTest {
            val useCaseResponse = MockedModelGenerator.getPostPerUserDomainList()

            coEvery { postsPerUserUseCase(any() as String, any() as String) } returns useCaseResponse

            val expected = listOf(MockedModelGenerator.getPost())
            viewModel.onEvent(PostViewModel.UiEvent.Refresh)

            val observer = viewModel.state.test(this)

            observer.assertValues(PostViewModel.State.Content(expected)).finish()
        }
    }

    @ExperimentalTime
    @Test
    fun `error state when something goes wrong`() {
        mainCoroutineRule.runBlockingTest {
            mockkStatic(Log::class)
            every { Log.e(any() as String, any() as String) } returns 1
            coEvery { postsPerUserUseCase(any() as String, any() as String) } throws Exception("")

            viewModel.onEvent(PostViewModel.UiEvent.Initialize)
            val observer = viewModel.state.test(this)

            observer.assertValues(PostViewModel.State.Error).finish()
        }
    }

}