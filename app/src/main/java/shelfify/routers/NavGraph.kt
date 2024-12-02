package shelfify.routers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import shelfify.be.services.viewModel.AdminViewModel
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.be.services.viewModel.BookViewModel
import shelfify.be.services.viewModel.CartViewModel
import shelfify.be.services.viewModel.HistoryViewModel
import shelfify.be.services.viewModel.MemberViewModel
import shelfify.be.services.viewModel.NotificationViewModel
import shelfify.be.services.viewModel.ReservationViewModel
import shelfify.ui.admin.bookData.ShowBookData
import shelfify.ui.admin.favoriteBook.ShowFavoriteData
import shelfify.ui.admin.historyMember.ShowHistoryMemberScreen
import shelfify.ui.admin.reservationData.ShowReservationData
import shelfify.ui.auth.changePassword.ShowChangePasswordScreen
import shelfify.ui.auth.forgotPassword.ShowForgotPasswordScreen
import shelfify.ui.auth.login.ShowLoginScreen
import shelfify.ui.auth.register.ShowRegisterScreen
import shelfify.ui.homeScreen.home.ShowHomeScreen
import shelfify.ui.homeScreen.member.cart.ShowCartScreen
import shelfify.ui.homeScreen.member.history.ShowHistoryScreen
import shelfify.ui.homeScreen.member.notification.ShowNotificationScreen
import shelfify.ui.homeScreen.member.profile.ShowProfileScreen
import shelfify.ui.homeScreen.member.setting.ShowSettingScreen
import shelfify.ui.homeScreen.searchScreen.ShowSearchScreen
import shelfify.ui.library.book.ShowBookScreen
import shelfify.ui.library.bookDetail.ShowBookDetail

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    authViewModel: AuthViewModel,
    bookViewModel: BookViewModel,
    memberViewModel: MemberViewModel,
    historyViewModel: HistoryViewModel,
    cartViewModel: CartViewModel,
    notificationViewModel: NotificationViewModel,
    reservationViewModel: ReservationViewModel,
    adminViewModel: AdminViewModel,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            ShowHomeScreen().Homepage(
                navController = navController,
                authViewModel = authViewModel,
                adminViewModel = adminViewModel
            )
        }



        composable(route = Screen.Login.route) {
            ShowLoginScreen().Login(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(route = Screen.Register.route) {
            ShowRegisterScreen().Register(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(route = Screen.ForgotPassword.route) {
            ShowForgotPasswordScreen().ForgotPassword(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(route = Screen.SearchScreen.route) {
            ShowSearchScreen().SearchScreen(
                navController = navController
            )
        }

        composable(route = Screen.Notification.route) {
            ShowNotificationScreen().NotificationScreen(
                notificationViewModel = notificationViewModel
            )
        }

        composable(route = Screen.FavoriteBook.route) {
            ShowFavoriteData().FavoriteData()
        }

        composable(route = Screen.ReservationData.route) {
            ShowReservationData().ReservationData()
        }

        composable(route = Screen.BookData.route) {
            ShowBookData().BookData(adminViewModel = adminViewModel)
        }

        composable(
            route = Screen.HistoryMember.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType },
                navArgument("fullName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            val fullName = backStackEntry.arguments?.getString("fullName") ?: ""
            ShowHistoryMemberScreen().HistoryMember(
                navController = navController,
                adminViewModel = adminViewModel,
                userId = userId,
                fullName = fullName
            )
        }

        composable(route = Screen.History.route) {
            ShowHistoryScreen().HistoryScreen(historyViewModel = historyViewModel)
        }

        composable(route = Screen.Profile.route) {
            ShowProfileScreen().ProfileScreen(
                navController = navController,
                authViewModel = authViewModel,
                memberViewModel = memberViewModel
            )
        }

        composable(route = Screen.Setting.route) {
            ShowSettingScreen().Setting(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(
            route = Screen.BookScreen.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            ShowBookScreen().BookScreen(
                navController = navController,
                bookViewModel = bookViewModel,
                category = category
            )
        }

        composable(
            route = Screen.ChangePassword.route,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ShowChangePasswordScreen().ChangePassword(
                navController = navController,
                authViewModel = authViewModel,
                email = email
            )
        }

        composable(
            route = Screen.BookDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            ShowBookDetail().BookDetail(
                navController = navController,
                bookViewModel = bookViewModel,
                cartViewModel = cartViewModel,
                reservationViewModel = reservationViewModel,
                id = id
            )
        }

        composable(route = Screen.Cart.route) {
            ShowCartScreen().Cart(
                navController = navController,
                cartViewModel = cartViewModel,
                reservationViewModel = reservationViewModel,
                bookViewModel = bookViewModel
            )
        }
    }
}
