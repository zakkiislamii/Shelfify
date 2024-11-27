package shelfify.ui.authUI.login.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp


@Composable
fun ToForgotPassword(onClick: () -> Unit) {
    Text(
        text = "Forgot password",
        color = Color(0xFF525252),
        modifier = androidx.compose.ui.Modifier.clickable { onClick() },
        fontSize = 11.sp
    )
}