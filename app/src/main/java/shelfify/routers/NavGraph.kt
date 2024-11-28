// NavGraph.kt
package shelfify.routers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.be.services.viewModel.BookViewModel
import shelfify.ui.authUI.changePassword.ShowChangePasswordScreen
import shelfify.ui.authUI.forgotPassword.ShowForgotPasswordScreen
import shelfify.ui.authUI.login.ShowLoginScreen
import shelfify.ui.authUI.register.ShowRegisterScreen
import shelfify.ui.homeScreen.ShowHomeScreen
import shelfify.ui.homeScreen.history.ShowHistoryScreen
import shelfify.ui.homeScreen.member.notification.ShowNotificationScreen
import shelfify.ui.homeScreen.member.notification.profile.ShowProfileScreen
import shelfify.ui.homeScreen.searchScreen.ShowSearchScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    authViewModel: AuthViewModel,
    bookViewModel: BookViewModel,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            ShowHomeScreen().Homepage(navController = navController)
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
            ShowNotificationScreen().NotificationScreen(

            )
        }

        composable(route = Screen.History.route) {
            ShowHistoryScreen().HistoryScreen(
                navController = navController
            )
        }

        composable(route = Screen.Profile.route) {
            ShowProfileScreen().ProfileScreen(
                navController = navController
            )
        }
    }
}