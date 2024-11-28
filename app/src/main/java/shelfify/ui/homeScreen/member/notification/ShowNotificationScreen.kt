package shelfify.ui.homeScreen.member.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import shelfify.ui.homeScreen.member.notification.components.NotificationItem
import shelfify.ui.homeScreen.member.notification.components.NotificationSection
import shelfify.ui.theme.MainColor

class ShowNotificationScreen {
    private val sections = listOf("Pending", "Borrowed", "Rejected", "Warning", "Returned")
    @Preview
    @Composable
    fun NotificationScreen() {
        Scaffold(
            topBar = {
                Text(
                    text = "Notifications",
                    color = MainColor,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(20.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        sections.forEach { section ->
                            item {
                                NotificationSection(section)
                            }
                            item {
                                NotificationItem("Pesan untuk $section", "Jam sekarang")
                            }
                        }
                    }
                }
            }
        )
    }
}
