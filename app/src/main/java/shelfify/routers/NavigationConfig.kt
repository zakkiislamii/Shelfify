package shelfify.routers

object NavigationConfig {
    val hidden = setOf(
        Screen.Auth.Login.route,
        Screen.Auth.Register.route,
        Screen.Auth.ForgotPassword.route,
        Screen.Auth.ChangePassword.route,
        Screen.Member.Setting.route,
        Screen.BookDetail.route,
        Screen.Member.Cart.route,
        Screen.Admin.HistoryMember.route,
        Screen.Admin.AddBook.route,
        Screen.Admin.EditBook.route,
        Screen.Admin.ViewDetails.route,
    )
}