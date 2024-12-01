package shelfify.routers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.be.services.viewModel.BookViewModel
import shelfify.be.services.viewModel.CartViewModel
import shelfify.be.services.viewModel.MemberViewModel
import shelfify.be.services.viewModel.ReservationViewModel
import shelfify.ui.admin.bookData.ShowBookData
import shelfify.ui.admin.favoriteBook.ShowFavoriteData
import shelfify.ui.admin.memberData.ShowMemberData
import shelfify.ui.admin.reservationData.ShowReservationData
import shelfify.ui.auth.changePassword.ShowChangePasswordScreen
import shelfify.ui.auth.forgotPassword.ShowForgotPasswordScreen
import shelfify.ui.auth.login.ShowLoginScreen
import shelfify.ui.auth.register.ShowRegisterScreen
import shelfify.ui.homeScreen.cart.ShowCartScreen
import shelfify.ui.homeScreen.history.ShowHistoryScreen
import shelfify.ui.homeScreen.home.ShowHomeScreen
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
    cartViewModel: CartViewModel,
    reservationViewModel: ReservationViewModel,
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
            )
        }

        composable(route = Screen.ChangePassword.route + "?email={email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ShowChangePasswordScreen().ChangePassword(
                navController = navController,
                authViewModel = authViewModel,
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
            ShowNotificationScreen().NotificationScreen()
        }

        composable(route = Screen.MemberData.route) {
            ShowMemberData().MemberData()
        }

        composable(route = Screen.FavoriteBook.route) {
            ShowFavoriteData().FavoriteData()
        }

        composable(route = Screen.ReservationData.route) {
            ShowReservationData().ReservationData()
        }

        composable(route = Screen.BookData.route) {
            ShowBookData().BookData()
        }


        composable(route = Screen.History.route) {
            ShowHistoryScreen().HistoryScreen(
                navController = navController
            )
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
                bookViewModel = bookViewModel
            )
        }

        composable(route = Screen.Setting.route) {
            ShowSettingScreen().Setting(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(
            route = Screen.BookDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType }) // Gunakan IntType
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            ShowBookDetail().BookDetail(
                navController = navController,
                bookViewModel = bookViewModel,
                cartViewModel = cartViewModel,
                reservationViewModel = reservationViewModel,
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