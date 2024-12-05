package shelfify.routers

object NavigationConfig {
    val hiddenNavbarRoutes = setOf(
        Screen.Login.route,
        Screen.Register.route,
        Screen.ForgotPassword.route,
        Screen.ChangePassword.route,
        Screen.Setting.route,
        Screen.BookDetail.route,
        Screen.Cart.route,
        Screen.HistoryMember.route,
        Screen.AddBook.route,
        Screen.EditBook.route
    )

    val hiddenTopBarRoutes = setOf(
        Screen.HistoryMember.route,
        Screen.AddBook.route,
        Screen.EditBook.route,
        Screen.BookDetail.route
    )
}