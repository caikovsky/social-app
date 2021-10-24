package life.league.challenge.kotlin.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import life.league.challenge.kotlin.BuildConfig
import life.league.challenge.kotlin.data.api.Api
import life.league.challenge.kotlin.data.repositories.LoginRepositoryImpl
import life.league.challenge.kotlin.data.repositories.PostRepositoryImpl
import life.league.challenge.kotlin.data.repositories.UserRepositoryImpl
import life.league.challenge.kotlin.domain.LoginRepository
import life.league.challenge.kotlin.domain.PostRepository
import life.league.challenge.kotlin.domain.UserRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val HOST = "https://engineering.league.dev/challenge/api/"

    @Provides
    fun provideBaseUrl() = HOST

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(Api::class.java)

}

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