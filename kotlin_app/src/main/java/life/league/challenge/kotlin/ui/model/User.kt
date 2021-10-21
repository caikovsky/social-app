package life.league.challenge.kotlin.ui.model

import life.league.challenge.kotlin.data.model.UserResponse

data class User(val id: Int, val thumbnail: String, val name: String)

fun UserResponse.toUiModel() = User(this.id, this.avatar.thumbnail, this.name)