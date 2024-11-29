package shelfify.routers

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Home : Screen("home")
    data object Register : Screen("register")
    data object ForgotPassword : Screen("forgotPassword")
    data object ChangePassword : Screen("changePassword")
    data object SearchScreen : Screen("searchScreen")
    data object Notification : Screen("notification")
    data object Profile : Screen("profile")
    data object History : Screen("history")
    data object Setting : Screen("setting")
    data object BookScreen : Screen("book/{category}")
    data object ReservationData : Screen("reservationData")
    data object MemberData : Screen("memberData")
    data object FavoriteBook : Screen("favoriteBook")
    data object BookData : Screen("bookData")
    data object BookDetail : Screen("bookDetail/{id}")
}