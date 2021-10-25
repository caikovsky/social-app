package life.league.challenge.kotlin.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import life.league.challenge.kotlin.data.api.Api
import life.league.challenge.kotlin.data.repositories.LoginRepositoryImpl
import life.league.challenge.kotlin.data.repositories.PostRepositoryImpl
import life.league.challenge.kotlin.data.repositories.UserRepositoryImpl
import life.league.challenge.kotlin.domain.repositories.LoginRepository
import life.league.challenge.kotlin.domain.repositories.PostRepository
import life.league.challenge.kotlin.domain.repositories.UserRepository

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun provideLoginRepository(service: Api): LoginRepository = LoginRepositoryImpl(service)

    @Provides
    fun provideUserRepository(service: Api): UserRepository = UserRepositoryImpl(service)

    @Provides
    fun providePostRepository(service: Api): PostRepository = PostRepositoryImpl(service)
}