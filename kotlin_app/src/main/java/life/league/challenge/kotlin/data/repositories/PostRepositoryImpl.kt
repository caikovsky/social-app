package life.league.challenge.kotlin.data.repositories

import life.league.challenge.kotlin.data.api.Api
import life.league.challenge.kotlin.data.model.PostResponse
import life.league.challenge.kotlin.domain.model.PostDomain
import life.league.challenge.kotlin.domain.repositories.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val api: Api) : PostRepository {
    override suspend fun invoke(apiKey: String, userId: Int): List<PostDomain> =
        api.posts(accessToken = apiKey, userId = userId).toDomain()

    private fun List<PostResponse>.toDomain(): List<PostDomain> =
        this.map { PostDomain(id = it.id, title = it.title, body = it.body) }
}
