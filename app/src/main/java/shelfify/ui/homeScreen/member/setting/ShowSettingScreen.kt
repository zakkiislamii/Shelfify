package shelfify.ui.homeScreen.member.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.contracts.session.UserSessionData
import shelfify.routers.Screen
import shelfify.ui.homeScreen.member.setting.components.SettingBody
import shelfify.ui.homeScreen.member.setting.components.SettingHeader
import shelfify.utils.proxy.RealUserSessionData
import shelfify.utils.proxy.UserSessionProxy
import shelfify.utils.response.Result
import shelfify.utils.toast.CustomToast

class ShowSettingScreen {
    @Composable
    fun Setting(navController: NavController, authViewModel: AuthViewModel) {
        val context = LocalContext.current
        val userSessionData: UserSessionData = UserSessionProxy(RealUserSessionData())
        val userSession = userSessionData.getUserSession(context)
        val logoutState = authViewModel.logOutState.collectAsState()
        val email = userSession.email.toString()

        // Handle logout state
        LaunchedEffect(logoutState) {
            when (logoutState.value) {
                is Result.Success -> {
                    // Show success toast
                    CustomToast().showToast(
                        context = context,
                        message = "Logout successfully"
                    )
                    // Navigate to login screen
                    navController.navigate(Screen.Login.route)
                }

                is Result.Error -> {
                    // Show error toast
                    CustomToast().showToast(
                        context = context,
                        message = "Error occurred"
                    )
                }

                else -> {}
            }
        }

        Scaffold(
            topBar = {
                SettingHeader(onClick = { navController.navigate(Screen.Profile.route) })
            },
            content = { paddingValues ->

                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(20.dp)
                ) {
                    Column {
                        SettingBody().ChangePasswordSetting {
                            navController.navigate(Screen.ChangePassword.route + "?email=${email}")
                        }
                        SettingBody().LogoutSetting(onClick = {
                            // Trigger logout
                            authViewModel.logout(email)
                            navController.navigate(Screen.Login.route)
                        })
                    }
                }
            }
        )
    }
}
