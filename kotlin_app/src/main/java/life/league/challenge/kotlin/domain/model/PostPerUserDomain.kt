package life.league.challenge.kotlin.domain.model

import life.league.challenge.kotlin.ui.model.Post

data class PostPerUserDomain(
    val userId: Int,
    val name: String,
    val thumbnail: String,
    val posts: List<PostDomain>,
)

fun PostPerUserDomain.toViewEntity() =
    Post(
        userId = this.userId,
        name = this.name,
        thumbnail = this.thumbnail,
        title = this.posts.last().title,
        body = this.posts.last().body
    )