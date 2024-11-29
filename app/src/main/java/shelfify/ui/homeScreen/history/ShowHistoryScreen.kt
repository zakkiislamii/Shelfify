package shelfify.ui.homeScreen.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import shelfify.ui.homeScreen.history.member.HistoryHeader

class ShowHistoryScreen {
    @Composable
    fun HistoryScreen(navController: NavController) {
        Scaffold(
            topBar = {
                HistoryHeader()
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