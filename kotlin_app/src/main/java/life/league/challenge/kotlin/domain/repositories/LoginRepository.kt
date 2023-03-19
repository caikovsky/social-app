package life.league.challenge.kotlin.domain.repositories

import life.league.challenge.kotlin.domain.model.AccountDomain

interface LoginRepository {
    suspend operator fun invoke(username: String, password: String): AccountDomain
}
