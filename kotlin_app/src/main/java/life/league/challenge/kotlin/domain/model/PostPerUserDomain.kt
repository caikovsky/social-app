package life.league.challenge.kotlin.domain.model

import life.league.challenge.kotlin.ui.model.Post

data class PostPerUserDomain(
    val userId: Int,
    val name: String,
    val thumbnail: String,
    val posts: List<PostDomain>,
)

