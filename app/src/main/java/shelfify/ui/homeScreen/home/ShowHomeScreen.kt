package shelfify.ui.homeScreen.home

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import shelfify.be.services.viewModel.AdminViewModel
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.contracts.enumerations.Role
import shelfify.routers.Screen
import shelfify.ui.layout.admin.AdminContent
import shelfify.ui.layout.member.MemberContent
import shelfify.utils.loading.LoadingIndicator
import shelfify.utils.proxy.RealUserSessionData
import shelfify.utils.proxy.UserSessionProxy
import shelfify.utils.response.Result
import shelfify.utils.toast.CustomToast

class ShowHomeScreen {
    @Composable
    fun Homepage(
        navController: NavController,
        authViewModel: AuthViewModel,
        adminViewModel: AdminViewModel,
    ) {
        val context = LocalContext.current
        val userSessionData = remember { UserSessionProxy(RealUserSessionData()) }
        val userSession = userSessionData.getUserSession(context)
        var fullName by remember { mutableStateOf("") }
        var firstName by remember { mutableStateOf("") }
        var isLoading by remember { mutableStateOf(false) }
        var error by remember { mutableStateOf<String?>(null) }
        val userState by authViewModel.getUserByEmailState.collectAsState()
        val scrollState = rememberLazyListState()
        val isScrolled = scrollState.firstVisibleItemIndex > 0 ||
                scrollState.firstVisibleItemScrollOffset > 0

        LaunchedEffect(userSession.role) {
            if (userSession.role.isNullOrEmpty() ||
                (userSession.role != Role.ADMIN.toString() &&
                        userSession.role != Role.MEMBER.toString())
            ) {
                navController.navigate(Screen.Auth.Login.route) {
                    popUpTo(0) { inclusive = true }
                }
                return@LaunchedEffect
            }
        }

        LaunchedEffect(Unit) {
            userSession.email?.let { email ->
                if (email.isNotEmpty()) {
                    authViewModel.getUserByEmail(email)
                }
            }
        }

        LaunchedEffect(userState) {
            when (userState) {
                is Result.Success -> {
                    val userData = (userState as Result.Success).data
                    fullName = userData.fullName
                    firstName = userData.fullName.split(" ")[0]
                    isLoading = false
                    error = null
                }

                is Result.Error -> {
                    isLoading = false
                    error = (userState as Result.Error).message
                    CustomToast().showToast(context, error ?: "")
                }

                is Result.Loading -> {
                    isLoading = true
                }
            }
        }
        when {
            isLoading -> {
                LoadingIndicator()
            }

            else -> {
                when (userSession.role) {
                    Role.ADMIN.toString() -> {
                        AdminContent(
                            adminViewModel = adminViewModel,
                            navController = navController
                        )
                    }

                    Role.MEMBER.toString() -> {
                        MemberContent(
                            firstName = firstName,
                            navController = navController,
                            adminViewModel = adminViewModel,
                            scrollState = scrollState,
                            isScrolled = isScrolled
                        )
                    }
                }
            }
        }
    }
}