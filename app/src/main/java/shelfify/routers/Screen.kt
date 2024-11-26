package shelfify.routers

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Home : Screen("home")
    data object Register : Screen("register")
    data object ForgotPassword : Screen("forgotPassword")
    data object ChangePassword : Screen("changePassword")
}