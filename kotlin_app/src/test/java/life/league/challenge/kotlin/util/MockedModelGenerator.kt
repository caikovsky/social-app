package life.league.challenge.kotlin.util

import life.league.challenge.kotlin.data.model.*
import life.league.challenge.kotlin.domain.model.PostDomain
import life.league.challenge.kotlin.domain.model.PostPerUserDomain

object MockedModelGenerator {
    fun getAccountResponse(token: String? = "apiKey"): AccountResponse = AccountResponse(
        apiKey = token
    )

    fun getUserResponseList(): List<UserResponse> = listOf(
        getUserResponse(),
        getUserResponse(id = 2, name = "Maxwell")
    )

    fun getUserResponse(
        id: Int = 1,
        name: String = "John Doe",
    ): UserResponse = UserResponse(
        id = id,
        avatar = AvatarResponse(large = "large_url", medium = "medium_url", thumbnail = "thumbnail_url"),
        name = name,
        username = "johndoe",
        email = "johndoe@email.com",
        address = AddressResponse(
            street = "street",
            suite = "suite",
            city = "city",
            zipcode = "zipcode",
            geoResponse = GeoResponse(
                lat = 0.0,
                lng = 0.0
            )
        ),
        phone = "12345",
        website = "http://website.com",
        company = CompanyResponse(
            name = "name",
            catchPhrase = "catchphrase",
            bs = "bs"
        )
    )

    fun getPostResponse(
        id: Int = 1,
        title: String = "title",
        body: String = "body",
    ): PostResponse = PostResponse(
        userId = 0,
        id = id,
        title = title,
        body = body
    )

    fun getPostResponseList(): List<PostResponse> = listOf(
        getPostResponse(),
        getPostResponse(id = 2),
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

    fun getPostDomainList(): List<PostDomain> = listOf(
        getPostDomain(),
        getPostDomain(id = 2)
    )

    fun getPostPerUserDomain(
        userId: Int = 1,
        name: String = "John Doe",
        thumbnail: String = "thumbnail_url",
        posts: List<PostDomain> = getPostDomainList(),
    ): PostPerUserDomain = PostPerUserDomain(
        userId = userId,
        name = name,
        thumbnail = thumbnail,
        posts = posts
    )

    fun getPostPerUserDomainList(): List<PostPerUserDomain> = listOf(
        getPostPerUserDomain(),
        getPostPerUserDomain(userId = 2, name = "Maxwell")
    )
}
