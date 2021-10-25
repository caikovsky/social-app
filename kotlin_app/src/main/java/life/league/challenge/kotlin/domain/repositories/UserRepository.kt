package life.league.challenge.kotlin.domain.repositories

import life.league.challenge.kotlin.domain.model.UserDomain

interface UserRepository {
    suspend operator fun invoke(apiKey: String): List<UserDomain>
}