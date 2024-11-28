package shelfify.ui.homeScreen.member.notification.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotificationItem(message: String, time: String) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = message,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = time,
            fontSize = 12.sp,
            modifier = Modifier.weight(0.5f)
        )
    }
}