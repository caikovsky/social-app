package life.league.challenge.kotlin.data.repositories

import life.league.challenge.kotlin.data.api.Api
import life.league.challenge.kotlin.data.database.dao.UserDao
import life.league.challenge.kotlin.data.model.local.UserEntity
import life.league.challenge.kotlin.data.model.remote.UserResponse
import life.league.challenge.kotlin.domain.model.UserDomain
import life.league.challenge.kotlin.domain.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val api: Api, private val userDao: UserDao) : UserRepository {
    override suspend fun invoke(apiKey: String): List<UserDomain> {
        val apiResult = api.users(accessToken = apiKey)
        return apiResult.toDomain()
        TODO("Redo cache logic")
    }

    @JvmName("toDomainUserEntity")
    private fun List<UserEntity>.toDomain(): List<UserDomain> =
        map { UserDomain(id = it.id, name = it.name, thumbnail = it.avatar) }

    @JvmName("toDomainUserResponse")
    private fun List<UserResponse>.toDomain(): List<UserDomain> =
        map { UserDomain(id = it.id, name = it.name, thumbnail = it.avatar.thumbnail) }

    private fun UserResponse.toEntity(): UserEntity =
        UserEntity(
            id = this.id,
            name = this.name,
            username = this.username,
            email = this.email,
            phone = this.phone,
            website = this.website,
            avatar = this.avatar.thumbnail
        )
}