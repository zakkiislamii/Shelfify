package shelfify.ui.homeScreen.member.setting

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.routers.Screen
import shelfify.ui.homeScreen.member.setting.components.SettingBody
import shelfify.ui.homeScreen.member.setting.components.SettingHeader

class ShowSettingScreen {
    @Composable
    fun Setting(navController: NavController, authViewModel: AuthViewModel) {
        val context = LocalContext.current
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "") ?: ""


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
                            navController.navigate("changePassword/${email}")
                        }
                        SettingBody().LogoutSetting(
                            onClick = {
                                authViewModel.logout(context)
                                navController.navigate(Screen.Login.route)
                            }
                        )
                    }
                }
            }
        )
    }
}