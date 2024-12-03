package shelfify.data.session

data class UserSession(
    val isLoggedIn: Boolean,
    val userId: Int,
    val email: String?,
    val role: String?,
)