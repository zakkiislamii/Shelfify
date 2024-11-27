package shelfify.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.shelfify.R
import shelfify.data.NavItem
import shelfify.ui.theme.MainColor

@Composable
private fun navbarItems() = listOf(
    NavItem.IconItem("Home", Icons.Rounded.Home),
    NavItem.DrawableItem("History", R.drawable.history),
    NavItem.IconItem("Notifications", Icons.Rounded.Notifications),
    NavItem.IconItem("Profile", Icons.Rounded.AccountCircle)
)

@Preview
@Composable
fun Navbar() {
    NavigationBar {
        Row(modifier = Modifier.background(Color.White)) {
            navbarItems().forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = index == 0,
                    onClick = { },
                    icon = {
                        when (item) {
                            is NavItem.IconItem -> Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = if (index == 0) MainColor else Color.DarkGray
                            )

                            is NavItem.DrawableItem -> Icon(
                                painter = painterResource(item.drawable),
                                contentDescription = item.title,
                                tint = if (index == 0) MainColor else Color.DarkGray
                            )
                        }
                    },
                    label = {
                        Text(
                            text = item.title,
                            color = if (index == 0) MainColor else Color.DarkGray,
                            fontWeight = if (index == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }
    }
}