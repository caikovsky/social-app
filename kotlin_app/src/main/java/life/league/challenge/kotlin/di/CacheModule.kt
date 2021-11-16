package life.league.challenge.kotlin.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import life.league.challenge.kotlin.data.database.CacheDatabase
import life.league.challenge.kotlin.data.database.dao.PostDao
import life.league.challenge.kotlin.data.database.dao.UserDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CacheModule {

    @Singleton
    @Provides
    fun provideCacheDatabase(@ApplicationContext context: Context): CacheDatabase {
        return Room
            .databaseBuilder(
                context,
                CacheDatabase::class.java,
                CacheDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: CacheDatabase): UserDao = database.userDao()

    @Singleton
    @Provides
    fun providePostDao(database: CacheDatabase): PostDao = database.postDao()
}