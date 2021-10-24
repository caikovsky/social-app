package life.league.challenge.kotlin.domain

import life.league.challenge.kotlin.data.model.AccountResponse
import life.league.challenge.kotlin.data.model.PostResponse
import life.league.challenge.kotlin.data.model.UserResponse
import javax.inject.Inject

class PostsPerUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(name: String, password: String): List<PostPerUserDomain> {
        val token = loginRepository(name, password)
        val users = userRepository(token.apiKey ?: "")

        val postsPerUser = mutableMapOf<UserResponse, List<PostDomain>>()

        for (user in users) {
            val posts = postRepository(token.apiKey ?: "", user.id)
                .map {
                    PostDomain(id = it.id, title = it.title, body = it.body)
                }.sortedBy { it.id }

            postsPerUser[user] = posts
        }

        return postsPerUser.map { map ->
            PostPerUserDomain(
                userId = map.key.id,
                name = map.key.name,
                thumbnail = map.key.avatar.thumbnail,
                posts = map.value
            )
        }
    }
}

interface LoginRepository {
    suspend operator fun invoke(username: String, password: String): AccountResponse
}

interface UserRepository {
    suspend operator fun invoke(apiKey: String): List<UserResponse>
}

interface PostRepository {
    suspend operator fun invoke(apiKey: String, userId: Int): List<PostResponse>
}