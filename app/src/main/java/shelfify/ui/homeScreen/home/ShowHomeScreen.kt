package shelfify.ui.homeScreen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.contracts.session.UserSessionData
import shelfify.routers.Screen
import shelfify.ui.homeScreen.home.components.HomeHeader
import shelfify.ui.library.categoryBook.CategoryBook
import shelfify.utils.loading.LoadingIndicator
import shelfify.utils.proxy.RealUserSessionData
import shelfify.utils.proxy.UserSessionProxy
import shelfify.utils.response.Result
import shelfify.utils.toast.CustomToast


class ShowHomeScreen {
    @Composable
    fun Homepage(
        navController: NavController,
        authViewModel: AuthViewModel,
    ) {
        val context = LocalContext.current
        val userSessionData: UserSessionData = UserSessionProxy(RealUserSessionData())
        val userSession = userSessionData.getUserSession(context)
        var fullName by remember { mutableStateOf("") }
        var firstName by remember { mutableStateOf("") }
        val userState by authViewModel.getUserByEmailState.collectAsState()

        LaunchedEffect(userSession.email) {
            userSession.email?.let { email ->
                authViewModel.getUserByEmail(email)
            }
        }

        // Handle user state in a side effect to avoid recomposition issues
        LaunchedEffect(userState) {
            when (userState) {
                is Result.Success -> {
                    val userData = (userState as Result.Success).data
                    fullName = userData.fullName
                    firstName = userData.fullName.split(" ")[0]
                }

                is Result.Error -> {
                    val error = (userState as Result.Error).message
                    CustomToast().showToast(
                        context = context,
                        message = error
                    )
                }

                is Result.Loading -> {
                    // Loading state handled in UI
                }
            }
        }

        when (userState) {
            is Result.Loading -> {
                LoadingIndicator()
            }

            else -> {
                // Main content
                when (userSession.role) {
                    "MEMBER" -> {
                        Scaffold(
                            topBar = {
                                HomeHeader(fullname = firstName, navController) {
                                    navController.navigate(Screen.Cart.route)
                                }
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

                    else -> {
                        Scaffold(
                            topBar = {
                                Text(text = "Selamat datang, Admin!")
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
                                    // Admin content
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}