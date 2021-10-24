package life.league.challenge.kotlin.data.repositories

import life.league.challenge.kotlin.data.api.Api
import life.league.challenge.kotlin.domain.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api: Api) : UserRepository {
    override suspend fun invoke(apiKey: String) = api.users(accessToken = apiKey)
}