package life.league.challenge.kotlin.ui.post

import android.util.Log
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import life.league.challenge.kotlin.domain.PostsPerUserUseCase
import life.league.challenge.kotlin.ui.post.PostViewModel.State
import life.league.challenge.kotlin.ui.post.PostViewModel.UiEvent
import life.league.challenge.kotlin.util.MainCoroutineRule
import life.league.challenge.kotlin.util.MockedModelGenerator
import life.league.challenge.kotlin.util.test
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PostViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val postsPerUserUseCase: PostsPerUserUseCase = mockk()
    private val viewModel = PostViewModel(postsPerUserUseCase)

    @Test
    fun `successfully retrieve the posts per user when initialize event is triggered`() = runTest {
        val useCaseResponse = MockedModelGenerator.getPostPerUserDomainList()

        coEvery { postsPerUserUseCase(any() as String, any() as String) } returns useCaseResponse

        val expected = listOf(MockedModelGenerator.getPost())
        val observer = viewModel.state.test(mainCoroutineRule)

        viewModel.onEvent(UiEvent.Initialize)

        observer.assertValues(State.Uninitialized, State.Content(expected)).finish()
        observer.assertValueCount(2)
    }

    @Test
    fun `successfully retrieve the posts per user when refresh event is triggered`() = runTest {
        val useCaseResponse = MockedModelGenerator.getPostPerUserDomainList()

        coEvery { postsPerUserUseCase(any() as String, any() as String) } returns useCaseResponse

        val expected = listOf(MockedModelGenerator.getPost())
        val observer = viewModel.state.test(mainCoroutineRule)

        viewModel.onEvent(UiEvent.Refresh)

        observer.assertValues(
            State.Uninitialized,
            State.Loading,
            State.Content(expected),
        ).finish()
        observer.assertValueCount(3)
    }

    @Test
    fun `error state when something goes wrong`() = runTest {
        mockkStatic(Log::class)
        every { Log.e(any() as String, any() as String) } returns 1
        coEvery { postsPerUserUseCase(any() as String, any() as String) } throws Exception("")

        viewModel.onEvent(UiEvent.Initialize)
        val observer = viewModel.state.test(mainCoroutineRule)

        observer.assertValues(State.Error).finish()
    }
}
