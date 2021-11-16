package life.league.challenge.kotlin.data.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
)