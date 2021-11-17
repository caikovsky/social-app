package life.league.challenge.kotlin.data.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import life.league.challenge.kotlin.data.database.CacheDatabase
import life.league.challenge.kotlin.data.model.local.UserEntity
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
class UserDaoTest {

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
    fun insertUserFetchList() = runBlockingTest {
        val userEntity = UserEntity(
            id = 1,
            name = "Leanne Graham",
            username = "Bret",
            email = "Sincere@april.biz",
            phone = "1-770-736-8031 x56442",
            website = "hildegard.org",
            avatar = "https://randomuser.me/api/portraits/thumb/men/84.jpg"
        )

        database.userDao().insertUser(userEntity)

        val expected = listOf(userEntity)
        val actual = database.userDao().getAllUsers()

        assertEquals(actual, expected)
        assertThat(actual.size, `is`(expected.size))
    }
}