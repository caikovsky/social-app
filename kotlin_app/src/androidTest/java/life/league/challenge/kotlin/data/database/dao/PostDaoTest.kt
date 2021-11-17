package life.league.challenge.kotlin.data.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import life.league.challenge.kotlin.data.database.CacheDatabase
import life.league.challenge.kotlin.data.model.local.PostEntity
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class PostDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: CacheDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CacheDatabase::class.java
        ).build()
    }

    @After
    fun tearDown() = database.close()

    @Test
    fun insertPostsToGivenUserAndRetrieveIt() = runBlockingTest {
        val postEntity = PostEntity(
            userId = 1,
            id = 1,
            title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
            body = "quia et suscipit\n" +
                    "suscipit recusandae consequuntur expedita et cum\n" +
                    "reprehenderit molestiae ut ut quas totam\n" +
                    "nostrum rerum est autem sunt rem eveniet architecto"
        )

        database.postDao().insertPost(postEntity)

        val expected = listOf(postEntity)
        val actual = database.postDao().getAllPostsFromUser(postEntity.userId)

        assertEquals(actual, expected)
        assertThat(actual.size, `is`(expected.size))
    }
}