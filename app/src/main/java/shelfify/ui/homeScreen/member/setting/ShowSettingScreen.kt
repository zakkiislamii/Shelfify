package shelfify.ui.homeScreen.member.setting

import android.content.Context
import android.widget.Toast
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
import shelfify.routers.Screen
import shelfify.ui.homeScreen.member.setting.components.SettingBody
import shelfify.ui.homeScreen.member.setting.components.SettingHeader
import shelfify.utils.response.Result
import shelfify.utils.toast.CustomToast

class ShowSettingScreen {
    @Composable
    fun Setting(navController: NavController, authViewModel: AuthViewModel) {
        val context = LocalContext.current
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "") ?: ""

//        val logoutState = authViewModel.logOutState.collectAsState()
//        LaunchedEffect(logoutState.value) {
//            when (logoutState.value) {
//                is Result.Success -> {
//                    navController.navigate(Screen.Login.route) {
//                        popUpTo(0) { inclusive = true }
//                    }
//                }
//
//                is Result.Error -> {
//                    CustomToast().showToast(
//                        context,
//                        "Gagal logout: ${(logoutState.value as Result.Error).message}",
//                        Toast.LENGTH_SHORT
//                    )
//                }
//
//                else -> {}
//            }
//        }

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
                            onClick = { authViewModel.logout(context) }
                        )
                    }
                }
            }
        )
    }
}