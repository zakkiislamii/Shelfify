package shelfify.ui.homeScreen.member.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import shelfify.routers.Screen
    import shelfify.ui.homeScreen.member.setting.components.SettingBody
import shelfify.ui.homeScreen.member.setting.components.SettingHeader

class ShowSettingScreen {
    @Composable
    fun Setting(navController: NavController) {
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
                        SettingBody().ChangePasswordSetting(onClick = {})
                        SettingBody().LogoutSetting(onClick = {})
                    }
                }
            }
        )
    }
}