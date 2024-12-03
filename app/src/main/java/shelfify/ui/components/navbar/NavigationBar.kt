package shelfify.ui.components.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shelfify.R
import shelfify.contracts.session.UserSessionData
import shelfify.data.navbar.NavItem
import shelfify.routers.Screen
import shelfify.ui.theme.MainColor
import shelfify.utils.proxy.RealUserSessionData
import shelfify.utils.proxy.UserSessionProxy

class NavigationBar {

    @Composable
    private fun navbarItemMembers() = listOf(
        NavItem.DrawableItem("Home", R.drawable.home),
        NavItem.DrawableItem("History", R.drawable.history),
        NavItem.DrawableItem("Notifications", R.drawable.notification),
        NavItem.DrawableItem("Profile", R.drawable.profile)
    )

    @Composable
    private fun navbarItemAdmins() = listOf(
        NavItem.DrawableItem("Member\nData", R.drawable.member),
        NavItem.DrawableItem("Book\nData", R.drawable.bookdata),
        NavItem.DrawableItem("Favorite\nBook", R.drawable.favorite),
        NavItem.DrawableItem("Reservation\nData", R.drawable.reservation)
    )

    @Composable
    fun Navbar(navController: NavController) {
        val context = LocalContext.current
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        val userSessionData: UserSessionData = UserSessionProxy(RealUserSessionData())
        val userSession = userSessionData.getUserSession(context)
        val navbarItems = if (userSession.role == "ADMIN") {
            navbarItemAdmins()
        } else {
            navbarItemMembers()
        }

        NavigationBar(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .shadow(1.dp, shape = androidx.compose.foundation.shape.RoundedCornerShape(1.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            ) {
                navbarItems.forEach { item ->
                    val isSelected = currentRoute == when (item.title) {
                        "Home" -> Screen.Home.route
                        "History" -> Screen.History.route
                        "Notifications" -> Screen.Notification.route
                        "Profile" -> Screen.Profile.route
                        "Member\nData" -> Screen.Home.route
                        "Book\nData" -> Screen.BookData.route
                        "Favorite\nBook" -> Screen.FavoriteBook.route
                        "Reservation\nData" -> Screen.ReservationData.route
                        else -> ""
                    }

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (isSelected) return@NavigationBarItem

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

                                "Member\nData" -> navController.navigate(Screen.Home.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }

                                "Book\nData" -> navController.navigate(Screen.BookData.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }

                                "Favorite\nBook" -> navController.navigate(Screen.FavoriteBook.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }

                                "Reservation\nData" -> navController.navigate(Screen.ReservationData.route) {
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
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                fontSize = 13.sp
                            )
                        },
                        modifier = Modifier
                            .weight(1f)

                    )
                }
            }
        }
    }
}
