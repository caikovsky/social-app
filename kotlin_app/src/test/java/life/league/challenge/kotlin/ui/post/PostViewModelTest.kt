package life.league.challenge.kotlin.ui.post

import android.util.Log
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import life.league.challenge.kotlin.domain.PostsPerUserUseCase
import life.league.challenge.kotlin.util.MainCoroutineRule
import life.league.challenge.kotlin.util.MockedModelGenerator
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
}