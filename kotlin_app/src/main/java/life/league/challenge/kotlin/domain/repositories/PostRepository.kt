package life.league.challenge.kotlin.domain.repositories

import life.league.challenge.kotlin.domain.model.PostDomain

interface PostRepository {
    suspend operator fun invoke(apiKey: String, userId: Int): List<PostDomain>
}