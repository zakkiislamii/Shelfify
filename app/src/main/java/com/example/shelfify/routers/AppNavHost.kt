package com.example.shelfify.routers

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shelfify.domain.database.ShelfifyDatabase
import com.example.shelfify.domain.repositories.UserRepository
import com.example.shelfify.services.viewModel.AuthViewModel
import com.example.shelfify.services.viewModelFactory.AuthViewModelFactory
import com.example.shelfify.ui.authUI.login.ShowLoginScreen
import com.example.shelfify.ui.authUI.register.ShowRegisterScreen
import com.example.shelfify.ui.home.ShowHomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    val context = LocalContext.current


    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

    // Setup Database, Repository dan ViewModel
    val userDao = ShelfifyDatabase.getInstance(context).userDao()
    val userRepository = UserRepository(userDao)
    val authViewModelFactory = AuthViewModelFactory(userRepository, context)
    val authViewModel: AuthViewModel = viewModel(factory = authViewModelFactory)

    // Tentukan startDestination berdasarkan status login
    val startDestination = if (isLoggedIn) Screen.Home.route else Screen.Login.route

    NavHost(navController = navController, startDestination = startDestination) {
        // Home Screen
        composable(route = Screen.Home.route) {
            ShowHomeScreen().Homepage()
        }

        // Login Screen
        composable(route = Screen.Login.route) {
            ShowLoginScreen().Login(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                authViewModel = authViewModel
            )
        }

        // Register Screen
        composable(route = Screen.Register.route) {
            ShowRegisterScreen().Register(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                authViewModel = authViewModel
            )
        }
    }
}
