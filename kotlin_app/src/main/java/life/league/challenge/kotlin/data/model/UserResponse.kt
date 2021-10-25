package life.league.challenge.kotlin.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val avatar: AvatarResponse,
    val name: String,
    val username: String,
    val email: String,
    val address: AddressResponse,
    val phone: String,
    val website: String,
    val company: CompanyResponse,
) {
    @Serializable
    data class AvatarResponse(
        val large: String,
        val medium: String,
        val thumbnail: String,
    )

    @Serializable
    data class AddressResponse(
        val street: String,
        val suite: String,
        val city: String,
        val zipcode: String,
        val geo: GeoResponse,
    )

    @Serializable
    data class CompanyResponse(
        val name: String,
        val catchPhrase: String,
        val bs: String,
    )

    @Serializable
    data class GeoResponse(
        val lat: Double,
        val lng: Double,
    )
}

