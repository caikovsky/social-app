package life.league.challenge.kotlin.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountResponse(
    @SerialName("api_key")
    val apiKey: String,
)
