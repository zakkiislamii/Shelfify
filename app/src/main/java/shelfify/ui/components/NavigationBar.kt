package shelfify.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shelfify.R
import shelfify.data.NavItem
import shelfify.routers.Screen
import shelfify.ui.theme.MainColor

class NavigationBar {

    @Composable
    private fun navbarItems() = listOf(
        NavItem.DrawableItem("Home", R.drawable.home),
        NavItem.DrawableItem("History", R.drawable.history),
        NavItem.DrawableItem("Notifications", R.drawable.notification),
        NavItem.DrawableItem("Profile", R.drawable.profile)
    )

    @Composable
    fun Navbar(navController: NavController) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        NavigationBar {
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .shadow(
                        1.dp,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(1.dp)
                    )
            ) {
                navbarItems().forEach { item ->
                    val isSelected = currentRoute == when (item.title) {
                        "Home" -> Screen.Home.route
                        "History" -> Screen.History.route
                        "Notifications" -> Screen.Notification.route
                        "Profile" -> Screen.Profile.route
                        else -> ""
                    }

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            // Jangan navigasi jika item yang sama sudah dipilih
                            if (isSelected) return@NavigationBarItem

                            // Navigasi sesuai item yang dipilih
                            when (item.title) {
                                "Home" -> navController.navigate(Screen.Home.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                "History" -> navController.navigate(Screen.History.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                "Notifications" -> navController.navigate(Screen.Notification.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                "Profile" -> navController.navigate(Screen.Profile.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(item.drawable),
                                contentDescription = item.title,
                                tint = if (isSelected) MainColor else Color.DarkGray
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                color = if (isSelected) MainColor else Color.DarkGray,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }


}