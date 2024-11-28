package shelfify.ui.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import shelfify.contracts.session.UserSessionData
import shelfify.ui.components.NavigationBar
import shelfify.ui.homeScreen.home.HomeHeader
import shelfify.ui.homeScreen.home.categoryBook.CategoryBook
import shelfify.utils.proxy.RealUserSessionData
import shelfify.utils.proxy.UserSessionProxy


class ShowHomeScreen {
    @Composable
    fun Homepage(navController: NavController) {
        val context = LocalContext.current
        val userSessionData: UserSessionData = UserSessionProxy(RealUserSessionData())
        val userSession = userSessionData.getUserSession(context)
        val fullNameState =
            navController.currentBackStackEntry?.arguments?.getString("full_name") ?: ""

        Scaffold(
            topBar = {
                when (userSession.role) {
                    "MEMBER" -> {
                        HomeHeader(fullname = fullNameState, navController)
                    }

                    else -> {
                        Text(text = "Selamat datang, Admin!")
                    }
                }
            },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CategoryBook(navController)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewHome() {
    val navController = rememberNavController()
    ShowHomeScreen().Homepage(navController)
}
