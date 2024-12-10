package shelfify.routers

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import shelfify.be.services.viewModelProvider.ViewModelProvider
import shelfify.contracts.enumerations.Role
import shelfify.ui.admin.memberData.components.MemberDataHeader
import shelfify.ui.components.navbar.NavigationBar
import shelfify.utils.proxy.RealUserSessionData
import shelfify.utils.proxy.UserSessionProxy
import shelfify.utils.response.Result
import shelfify.utils.toast.CustomToast

@Composable
fun AppNavHost(navController: NavHostController) {
    val context = LocalContext.current
    val viewModels = ViewModelProvider.getInstance(context)
        .createViewModels(LocalViewModelStoreOwner.current!!)
    val userSessionData = remember { UserSessionProxy(RealUserSessionData()) }
    val userSession = userSessionData.getUserSession(context)
    var startDestination by remember { mutableStateOf(Screen.Auth.Login.route) }

    LaunchedEffect(Unit) {
        val sharedPrefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPrefs.getBoolean("is_logged_in", false)
        val hasValidRole = sharedPrefs.getString("role", null)?.let { role ->
            role in listOf(Role.ADMIN.toString(), Role.MEMBER.toString())
        } ?: false

        startDestination = if (isLoggedIn && hasValidRole) {
            Screen.Home.route
        } else {
            Screen.Auth.Login.route
        }
    }

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val hidden = currentRoute in NavigationConfig.hidden
    val logoutState by viewModels.authViewModel.logoutState.collectAsState()
    LaunchedEffect(logoutState) {
        when (logoutState) {
            is Result.Success -> {
                navController.navigate(Screen.Auth.Login.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        inclusive = true
                        saveState = false
                    }
                    restoreState = false
                    launchSingleTop = true
                }
            }
            is Result.Error -> {
                CustomToast().showToast(
                    context,
                    (logoutState as Result.Error).message
                )
            }
            else -> {}
        }
    }

    Scaffold(
        bottomBar = {
            if (!hidden) {
                if (userSession.isLoggedIn) {
                    NavigationBar().Navbar(navController)
                }
            }
        },
        topBar = {
            if (!hidden) {
                if (userSession.role == Role.ADMIN.toString()) {
                    MemberDataHeader {
                        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                            .edit()
                            .clear()
                            .apply()
                        viewModels.authViewModel.logout(context)
                    }
                }
            }
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            startDestination = startDestination,
            viewModels = viewModels,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
