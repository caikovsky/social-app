package life.league.challenge.kotlin.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val avatar: String,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String,
)