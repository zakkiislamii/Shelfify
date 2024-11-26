package com.example.shelfify.routers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shelfify.domain.database.ShelfifyDatabase
import com.example.shelfify.domain.repositories.UserRepository
import com.example.shelfify.services.controllers.UserController
import com.example.shelfify.services.proxy.UserServiceProxy
import com.example.shelfify.services.viewModel.ProxyViewModel
import com.example.shelfify.ui.home.ShowHomeScreen
import com.example.shelfify.ui.authUI.login.ShowLoginScreen
import com.example.shelfify.ui.authUI.register.ShowRegisterScreen
import com.example.shelfify.services.state.LoginState
import com.example.shelfify.services.viewModel.AuthViewModel

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    val context = LocalContext.current
    val userDao = ShelfifyDatabase.getInstance(context).userDao()
    val userRepository = UserRepository(userDao)
    val userController = UserController(userRepository)
    val userServiceProxy = UserServiceProxy(userController)
    val proxyViewModel: ProxyViewModel = viewModel { ProxyViewModel(userServiceProxy) }
    val authViewModel: AuthViewModel = viewModel { AuthViewModel(userController) }

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        // Home Screen
        composable(route = Screen.Home.route) {
            LaunchedEffect(Unit) {
                when (val state = proxyViewModel.loginState.value) {
                    is LoginState.Success -> {
                        val isLoggedIn = proxyViewModel.checkLoginStatus(state.user.userId)
                        if (!isLoggedIn) {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                            }
                        }
                    }
                    else -> {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }
                }
            }

            ShowHomeScreen().Homepage()
        }

        // Login Screen
        composable(route = Screen.Login.route) {
            ShowLoginScreen().Login(
                viewModel = proxyViewModel,
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
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
