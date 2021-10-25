package life.league.challenge.kotlin.domain

import life.league.challenge.kotlin.domain.model.PostDomain
import life.league.challenge.kotlin.domain.model.PostPerUserDomain
import life.league.challenge.kotlin.domain.model.UserDomain
import life.league.challenge.kotlin.domain.repositories.LoginRepository
import life.league.challenge.kotlin.domain.repositories.PostRepository
import life.league.challenge.kotlin.domain.repositories.UserRepository
import javax.inject.Inject

class PostsPerUserUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(name: String = "", password: String = ""): List<PostPerUserDomain> {
        val token = loginRepository(name, password)
        val users = userRepository(token.apiKey)

        val postsPerUserMap = mutableMapOf<UserDomain, List<PostDomain>>()

        for (user in users) {
            val posts = postRepository(token.apiKey, user.id).sortedBy { it.id }
            postsPerUserMap[user] = posts
        }

        return postsPerUserMap.map { entry ->
            PostPerUserDomain(
                userId = entry.key.id,
                name = entry.key.name,
                thumbnail = entry.key.thumbnail,
                posts = entry.value
            )
        }
    }
}





