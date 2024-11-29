package shelfify.ui.homeScreen.member.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import shelfify.routers.Screen
import shelfify.ui.homeScreen.member.profile.components.ProfileHeader

class ShowProfileScreen {

    @Composable
    fun ProfileScreen(navController: NavController) {
        Scaffold(
            topBar = {
                ProfileHeader(onClick = { navController.navigate(Screen.Setting.route) })
            },
            content = { paddingValues ->

                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(20.dp)
                ) {
                    Text(text = "Lorem")

                }
            }
        )
    }
}