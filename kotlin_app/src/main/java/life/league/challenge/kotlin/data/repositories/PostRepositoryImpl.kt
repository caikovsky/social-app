package life.league.challenge.kotlin.data.repositories

import life.league.challenge.kotlin.data.api.Api
import life.league.challenge.kotlin.data.database.dao.PostDao
import life.league.challenge.kotlin.data.model.remote.PostResponse
import life.league.challenge.kotlin.data.model.local.PostEntity
import life.league.challenge.kotlin.domain.model.PostDomain
import life.league.challenge.kotlin.domain.repositories.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val api: Api, private val postDao: PostDao) : PostRepository {
    override suspend fun invoke(apiKey: String, userId: Int): List<PostDomain> {
        val daoResult = postDao.getAllPostsFromUser(userId)

        return if (daoResult.isNullOrEmpty()) {
            val apiResult = api.posts(accessToken = apiKey, userId = userId)

            for (post in apiResult) {
                postDao.insertPost(post.toEntity())
            }

            apiResult.toDomain()
        } else {
            daoResult.toDomain()
        }

    }

    @JvmName("toDomainPostResponse")
    private fun List<PostResponse>.toDomain(): List<PostDomain> =
        this.map { PostDomain(id = it.id, title = it.title, body = it.body) }

    @JvmName("toDomainPostEntity")
    private fun List<PostEntity>.toDomain(): List<PostDomain> =
        this.map { post -> PostDomain(id = post.id, title = post.title, body = post.body) }

    private fun PostResponse.toEntity(): PostEntity =
        PostEntity(userId = this.userId, id = this.id, title = this.title, body = this.body)
}