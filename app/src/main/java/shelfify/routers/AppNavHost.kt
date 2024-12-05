package shelfify.routers

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import shelfify.be.services.viewModel.AdminViewModel
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.be.services.viewModel.BookViewModel
import shelfify.be.services.viewModel.CartViewModel
import shelfify.be.services.viewModel.HistoryViewModel
import shelfify.be.services.viewModel.MemberViewModel
import shelfify.be.services.viewModel.NotificationViewModel
import shelfify.be.services.viewModel.ReservationViewModel
import shelfify.be.services.viewModelProvider.ViewModelProvider
import shelfify.contracts.enumerations.Role
import shelfify.ui.admin.memberData.components.MemberDataHeader
import shelfify.ui.components.navbar.NavigationBar

// UserSessionManager.kt
class UserSessionManager(private val context: Context) {
    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    val isLoggedIn: Boolean get() = prefs.getBoolean("is_logged_in", false)
    val userRole: String get() = prefs.getString("role", "") ?: ""
}

@Composable
fun AppNavHost(navController: NavHostController) {
    val context = LocalContext.current
    val sessionManager = remember { UserSessionManager(context) }
    val viewModels = ViewModelProvider(context).createViewModels(LocalViewModelStoreOwner.current!!)

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val hideNavbar = currentRoute in NavigationConfig.hiddenNavbarRoutes
    val hideTopBar = currentRoute in NavigationConfig.hiddenTopBarRoutes

    Scaffold(
        bottomBar = {
            if (!hideNavbar && sessionManager.isLoggedIn) {
                NavigationBar().Navbar(navController)
            }
        },
        topBar = {
            if (sessionManager.userRole == Role.ADMIN.toString() && !hideTopBar && sessionManager.isLoggedIn) {
                MemberDataHeader {
                    viewModels.authViewModel.logout(context)
                }
            }
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            startDestination = Screen.Home.route,
            viewModels = viewModels,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

data class DataViewModel(
    val authViewModel: AuthViewModel,
    val bookViewModel: BookViewModel,
    val memberViewModel: MemberViewModel,
    val cartViewModel: CartViewModel,
    val historyViewModel: HistoryViewModel,
    val reservationViewModel: ReservationViewModel,
    val notificationViewModel: NotificationViewModel,
    val adminViewModel: AdminViewModel,
)
