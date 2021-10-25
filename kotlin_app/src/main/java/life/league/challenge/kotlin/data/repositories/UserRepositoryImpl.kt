package life.league.challenge.kotlin.data.repositories

import life.league.challenge.kotlin.data.api.Api
import life.league.challenge.kotlin.data.model.UserResponse
import life.league.challenge.kotlin.domain.model.UserDomain
import life.league.challenge.kotlin.domain.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api: Api) : UserRepository {
    override suspend fun invoke(apiKey: String) = api.users(accessToken = apiKey).toDomain()

    private fun List<UserResponse>.toDomain(): List<UserDomain> =
        map { UserDomain(id = it.id, name = it.name, thumbnail = it.avatar.thumbnail) }
}