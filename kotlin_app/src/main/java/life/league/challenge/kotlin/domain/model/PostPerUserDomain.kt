package life.league.challenge.kotlin.domain.model

data class PostPerUserDomain(
    val userId: Int,
    val name: String,
    val thumbnail: String,
    val posts: List<PostDomain>,
)