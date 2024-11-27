package shelfify.be.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import shelfify.contracts.enumerations.Role
import java.util.Date

@Entity(
    tableName = "Users",
    indices = [Index(value = ["email"], unique = true)]
)

data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: Int = 0,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,

    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "faculty")
    val faculty: String,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "role")
    val role: Role,

    @ColumnInfo(name = "profile_image")
    val profileImage: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: Date,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Date,

    @ColumnInfo(name = "is_logged_in")
    val isLoggedIn: Boolean = false,
)

