package shelfify.ui.member.setting

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.routers.Screen
import shelfify.ui.member.setting.components.SettingBody
import shelfify.ui.member.setting.components.SettingHeader
import shelfify.utils.proxy.RealUserSessionData
import shelfify.utils.proxy.UserSessionProxy
import shelfify.utils.response.Result
import shelfify.utils.toast.CustomToast

class ShowSettingScreen {
    @Composable
    fun Setting(navController: NavController, authViewModel: AuthViewModel) {
        val context = LocalContext.current
        val userSessionData = remember { UserSessionProxy(RealUserSessionData()) }
        val email = userSessionData.getUserSession(context).email ?: ""
        val logoutState by authViewModel.logoutState.collectAsState()

        BackHandler {
            navController.popBackStack()
        }

        LaunchedEffect(logoutState) {
            when (logoutState) {
                is Result.Success -> {
                    navController.navigate(Screen.Auth.Login.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                            saveState = false
                        }
                        restoreState = false
                        launchSingleTop = true
                    }
                }

                is Result.Error -> {
                    CustomToast().showToast(
                        context,
                        (logoutState as Result.Error).message
                    )
                }

                else -> {}
            }
        }

        Scaffold(
            topBar = {
                SettingHeader(
                    onClick = { navController.popBackStack() }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(20.dp)
            ) {
                Column {
                    SettingBody().ChangePasswordSetting {
                        navController.navigate("changePassword/${email}")
                    }

                    SettingBody().LogoutSetting {
                        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                            .edit()
                            .clear()
                            .apply()
                        authViewModel.logout(context)
                    }
                }
            }
        }
    }
}