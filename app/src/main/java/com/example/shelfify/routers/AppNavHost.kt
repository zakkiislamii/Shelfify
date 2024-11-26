package com.example.shelfify.routers

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shelfify.ui.home.ShowHomeScreen
import com.example.shelfify.ui.authUI.login.ShowLoginScreen
import com.example.shelfify.ui.authUI.register.ShowRegisterScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        // Login Screen
        composable(route = Screen.Login.route) {
            ShowLoginScreen().Login(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route)
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        // Register Screen
        composable(route = Screen.Register.route) {
            ShowRegisterScreen().Register(onNavigateToLogin = {
                navController.navigate(Screen.Login.route)
            })
        }

        // Home Screen
        composable(route = Screen.Home.route) {
            ShowHomeScreen().Homepage()
        }

        // Optional: Add Profile or Other Screens Here
        // composable(route = Screen.Profile.route) {
        //     ProfileScreen(onNavigateBack = {
        //         navController.popBackStack()
        //     })
        // }
    }
}
