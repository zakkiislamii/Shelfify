package shelfify.ui.homeScreen.member.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import shelfify.be.services.viewModel.NotificationViewModel
import shelfify.contracts.enumerations.Status
import shelfify.contracts.session.UserSessionData
import shelfify.ui.homeScreen.member.notification.components.NotificationItem
import shelfify.ui.homeScreen.member.notification.components.NotificationSection
import shelfify.ui.theme.MainColor
import shelfify.utils.proxy.RealUserSessionData
import shelfify.utils.proxy.UserSessionProxy
import java.text.SimpleDateFormat
import java.util.Locale

class ShowNotificationScreen {
    @Composable
    fun NotificationScreen(notificationViewModel: NotificationViewModel) {
        val context = LocalContext.current
        val userSessionData: UserSessionData = UserSessionProxy(RealUserSessionData())
        val userSession = userSessionData.getUserSession(context)
        val userId = userSession.userId.toInt()
        val notifications by notificationViewModel.getNotificationsByUserId(userId)
            .collectAsState(initial = emptyList())
        val groupedNotifications = notifications.groupBy { it.reservationStatus }

        Scaffold(
            topBar = {
                Text(
                    text = "Notifications",
                    color = MainColor,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(20.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    groupedNotifications.forEach { (status, statusNotifications) ->
                        val title = when (status) {
                            Status.PENDING -> "Pending Notifications"
                            Status.REJECTED -> "Rejected Notifications"
                            Status.BORROWED -> "Borrowed Notifications"
                            Status.RETURNED -> "Returned Notifications"
                        }

                        item {
                            NotificationSection(title = title)
                        }

                        items(statusNotifications.size) { index ->
                            val notification = statusNotifications[index]
                            val dateFormat =
                                SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                            val formattedTime =
                                notification.createdAt.let { dateFormat.format(it) } ?: "-"

                            NotificationItem(
                                message = notification.message,
                                time = formattedTime
                            )
                        }
                    }
                }
            }
        }
    }
}