package shelfify.routers

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import shelfify.be.database.ShelfifyDatabase
import shelfify.be.domain.repositories.UserRepository
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.be.services.viewModelFactory.AuthViewModelFactory
import shelfify.ui.authUI.changePassword.ShowChangePasswordScreen
import shelfify.ui.authUI.forgotPassword.ShowForgotPasswordScreen
import shelfify.ui.authUI.login.ShowLoginScreen
import shelfify.ui.authUI.register.ShowRegisterScreen
import shelfify.ui.home.ShowHomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
    val userDao = ShelfifyDatabase.getInstance(context).userDao()
    val userRepository = UserRepository(userDao)
    val authViewModelFactory = AuthViewModelFactory(userRepository, context)
    val authViewModel: AuthViewModel = viewModel(factory = authViewModelFactory)

    val startDestination = if (isLoggedIn) Screen.Home.route else Screen.Login.route

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Home.route + "?full_name={full_name}") { backStackEntry ->
            val fullName = backStackEntry.arguments?.getString("full_name") ?: ""
            ShowHomeScreen().Homepage(navController = navController)
        }

        composable(route = Screen.ChangePassword.route + "?email={email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ShowChangePasswordScreen().ChangePassword(
                navController = navController,
                authViewModel = authViewModel
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
    }
}