package life.league.challenge.kotlin.data.repositories

import life.league.challenge.kotlin.data.api.Api
import life.league.challenge.kotlin.data.model.PostResponse
import life.league.challenge.kotlin.domain.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val api: Api) : PostRepository {
    override suspend fun invoke(apiKey: String, userId: Int): List<PostResponse> = api.posts(accessToken = apiKey, userId = userId)
}