package life.league.challenge.kotlin.ui.model

data class Post(
    val userId: Int,
    val name: String,
    val thumbnail: String,
    val title: String,
    val body: String
)