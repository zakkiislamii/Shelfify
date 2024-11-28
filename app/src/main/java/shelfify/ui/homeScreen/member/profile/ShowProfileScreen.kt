package shelfify.ui.homeScreen.member.notification.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

class ShowProfileScreen {

    @Composable
    fun ProfileScreen(navController: NavController) {
        Scaffold(
            topBar = {
                Text(text = "Profile")
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