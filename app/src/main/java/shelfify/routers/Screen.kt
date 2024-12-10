package shelfify.routers

sealed class Screen(val route: String) {
    data object Auth : Screen("auth") {
        data object Login : Screen("login")
        data object Register : Screen("register")
        data object ForgotPassword : Screen("forgotPassword")
        data object ChangePassword : Screen("changePassword/{email}")
    }

    data object Member : Screen("member") {
        data object Notification : Screen("notification")
        data object Profile : Screen("profile")
        data object History : Screen("history")
        data object Setting : Screen("setting")
        data object Cart : Screen("cart")
        data object SearchScreen : Screen("searchScreen")
        data object BookScreen : Screen("book/{category}")
    }

    data object Admin : Screen("admin") {
        data object HistoryMember : Screen("historyMember/{userId}/{fullName}")
        data object AddBook : Screen("addBook")
        data object EditBook : Screen("editBook/{bookId}")
        data object ViewDetails :
            Screen("viewDetails/{fullName}/{title}/{writer}/{reservationId}/{bookImage}/{userId}/{bookId}")

        data object FavoriteBook : Screen("favoriteBook")
        data object BookData : Screen("bookData")
        data object ReservationData : Screen("reservationData")
    }

    data object Home : Screen("home")
    data object BookDetail : Screen("bookDetail/{id}")
}