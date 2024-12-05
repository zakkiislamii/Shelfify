package shelfify.ui.homeScreen.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import shelfify.be.services.viewModel.AdminViewModel
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.contracts.enumerations.Role
import shelfify.routers.Screen
import shelfify.ui.admin.memberData.components.MemberDataBody
import shelfify.ui.homeScreen.home.components.HomeHeader
import shelfify.ui.library.categoryBook.CategoryBook
import shelfify.utils.loading.LoadingIndicator
import shelfify.utils.response.Result

class ShowHomeScreen {
    @Composable
    fun Homepage(
        navController: NavController,
        authViewModel: AuthViewModel,
        adminViewModel: AdminViewModel,
    ) {
        val context = LocalContext.current
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
        val email = sharedPreferences.getString("email", "") ?: ""
        val role = sharedPreferences.getString("role", "") ?: ""
        var fullName by remember { mutableStateOf("") }
        var firstName by remember { mutableStateOf("") }
        val userState by authViewModel.getUserByEmailState.collectAsState()
        // Get user data saat pertama kali masuk
        LaunchedEffect(Unit) {
            if (email.isNotEmpty()) {
                authViewModel.getUserByEmail(email)
            }
        }

        // Handle user data state
        LaunchedEffect(userState) {
            when (userState) {
                is Result.Success -> {
                    val userData = (userState as Result.Success).data
                    fullName = userData.fullName
                    firstName = userData.fullName.split(" ")[0]
                }

                is Result.Error -> {
                    // Handle error silently
                }

                is Result.Loading -> {}
            }
        }

        when (userState) {
            is Result.Loading -> {
                LoadingIndicator()
            }

            else -> {
                when (role) {
                    Role.MEMBER.toString() -> {
                        Scaffold(
                            topBar = {
                                HomeHeader(
                                    fullname = firstName,
                                    onClick = {
                                        navController.navigate(Screen.Cart.route)
                                    }
                                )
                            },
                        ) { paddingValues ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White)
                                    .padding(paddingValues)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    CategoryBook(navController)
                                }
                            }
                        }
                    }

                    Role.ADMIN.toString() -> {
                        Scaffold { paddingValues ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White)
                                    .padding(paddingValues)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    MemberDataBody(
                                        adminViewModel = adminViewModel,
                                        navController = navController
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}