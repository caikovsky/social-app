package life.league.challenge.kotlin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import life.league.challenge.kotlin.data.database.dao.PostDao
import life.league.challenge.kotlin.data.database.dao.UserDao
import life.league.challenge.kotlin.data.model.local.PostEntity
import life.league.challenge.kotlin.data.model.local.UserEntity

@Database(version = 1, entities = [UserEntity::class, PostEntity::class])
abstract class CacheDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun postDao(): PostDao

    companion object {
        const val DATABASE_NAME: String = "cache_db"
    }
}