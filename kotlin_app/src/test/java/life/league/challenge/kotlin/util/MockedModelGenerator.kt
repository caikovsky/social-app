package life.league.challenge.kotlin.util

import life.league.challenge.kotlin.domain.model.AccountDomain
import life.league.challenge.kotlin.domain.model.PostDomain
import life.league.challenge.kotlin.domain.model.PostPerUserDomain
import life.league.challenge.kotlin.domain.model.UserDomain
import life.league.challenge.kotlin.ui.model.Post

object MockedModelGenerator {
    fun getAccountDomain(token: String = "apiKey"): AccountDomain =
        AccountDomain(
            apiKey = token
        )

    fun getUserDomain(
        id: Int = 1,
        name: String = "John Doe",
        thumbnail: String = "thumbnail_url",
    ): UserDomain = UserDomain(
        id = id,
        name = name,
        thumbnail = thumbnail
    )

    fun getPostDomain(
        id: Int = 1,
        title: String = "title",
        body: String = "body",
    ): PostDomain = PostDomain(
        id = id,
        title = title,
        body = body
    )

    fun getPostPerUserDomain(
        userId: Int = 1,
        name: String = "John Doe",
        thumbnail: String = "thumbnail_url",
        posts: List<PostDomain> = listOf(getPostDomain()),
    ): PostPerUserDomain = PostPerUserDomain(
        userId = userId,
        name = name,
        thumbnail = thumbnail,
        posts = posts
    )

    fun getPostPerUserDomainList(): List<PostPerUserDomain> = listOf(getPostPerUserDomain())

    fun getPost(): Post = Post(
        userId = 1,
        name = "John Doe",
        thumbnail = "thumbnail_url",
        title = "title",
        body = "body"
    )
}
