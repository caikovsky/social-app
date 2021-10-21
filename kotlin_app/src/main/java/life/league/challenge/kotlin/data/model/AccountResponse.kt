package life.league.challenge.kotlin.data.model

import com.google.gson.annotations.SerializedName

data class AccountResponse(@SerializedName("api_key") val apiKey: String? = null)
