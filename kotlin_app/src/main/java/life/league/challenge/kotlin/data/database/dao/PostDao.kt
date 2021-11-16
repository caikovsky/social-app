package life.league.challenge.kotlin.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import life.league.challenge.kotlin.data.model.local.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM posts")
    suspend fun getAllPosts(): List<PostEntity>

    @Query("SELECT * FROM posts WHERE userId = :userId")
    suspend fun getAllPostsFromUser(userId: Int): List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity)

}
