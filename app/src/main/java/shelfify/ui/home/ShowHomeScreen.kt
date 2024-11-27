package shelfify.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import shelfify.contracts.session.UserSessionData
import shelfify.ui.home.components.HomeHeader
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
        when (userSession.role) {
            "MEMBER" -> {
                HomeHeader(fullname = fullNameState)

            }

            else -> {
                Text(text = "Selamat datang, Admin!")
            }
        }
    }
}
