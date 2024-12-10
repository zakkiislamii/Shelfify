package shelfify.ui.member.setting.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shelfify.R
import com.example.shelfify.R.color.black

class SettingBody {
    @Composable
    fun ChangePasswordSetting(onClick: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { onClick() },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.changepassword),
                contentDescription = "Change Password",
                tint = colorResource(id = black),
                modifier = Modifier
                    .padding(end = 8.dp)

            )
            Text(text = "Change Password")
        }
    }

    @Composable
    fun LogoutSetting(
        enabled: Boolean = true,
        onClick: () -> Unit,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(enabled = enabled) { onClick() },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "Logout",
                tint = if (enabled) colorResource(id = black) else Color.Gray,
                modifier = Modifier
                    .padding(end = 8.dp)
            )
            Text(
                text = "Log Out",
                color = if (enabled) Color.Black else Color.Gray
            )
        }
    }
}

