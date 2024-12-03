package shelfify.routers

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import shelfify.be.database.ShelfifyDatabase
import shelfify.be.domain.repositories.BookRepository
import shelfify.be.domain.repositories.CartRepository
import shelfify.be.domain.repositories.HistoryRepository
import shelfify.be.domain.repositories.NotificationRepository
import shelfify.be.domain.repositories.ReservationRepository
import shelfify.be.domain.repositories.UserRepository
import shelfify.be.services.viewModel.AdminViewModel
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.be.services.viewModel.BookViewModel
import shelfify.be.services.viewModel.CartViewModel
import shelfify.be.services.viewModel.HistoryViewModel
import shelfify.be.services.viewModel.MemberViewModel
import shelfify.be.services.viewModel.NotificationViewModel
import shelfify.be.services.viewModel.ReservationViewModel
import shelfify.be.services.viewModelFactory.ViewModelFactory
import shelfify.ui.admin.memberData.components.MemberDataHeader
import shelfify.ui.components.navbar.NavigationBar
import shelfify.utils.response.Result
import shelfify.utils.toast.CustomToast


@Composable
fun AppNavHost(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
    val role = sharedPreferences.getString("role", "") ?: ""
    val email = sharedPreferences.getString("email", "") ?: ""
    val startDestination = if (isLoggedIn) {
        Screen.Home.route
    } else {
        Screen.Login.route
    }

    // Database and ViewModels setup
    val database = ShelfifyDatabase.getInstance(context)
    val viewModelFactory = remember {
        ViewModelFactory(
            UserRepository(database.userDao()),
            BookRepository(database.bookDao()),
            CartRepository(database.cartDao()),
            NotificationRepository(database.notificationDao()),
            ReservationRepository(database.reservationDao()),
            HistoryRepository(database.historyDao())
        )
    }

    // Initialize ViewModels
    val authViewModel: AuthViewModel = viewModel(factory = viewModelFactory)
    val bookViewModel: BookViewModel = viewModel(factory = viewModelFactory)
    val cartViewModel: CartViewModel = viewModel(factory = viewModelFactory)
    val adminViewModel: AdminViewModel = viewModel(factory = viewModelFactory)
    val historyViewModel: HistoryViewModel = viewModel(factory = viewModelFactory)
    val reservationViewModel: ReservationViewModel = viewModel(factory = viewModelFactory)
    val notificationViewModel: NotificationViewModel = viewModel(factory = viewModelFactory)
    val memberViewModel: MemberViewModel = viewModel(factory = viewModelFactory)

    // Wrap ViewModels in DataViewModel
    val viewModels = DataViewModel(
        authViewModel = authViewModel,
        bookViewModel = bookViewModel,
        memberViewModel = memberViewModel,
        cartViewModel = cartViewModel,
        historyViewModel = historyViewModel,
        reservationViewModel = reservationViewModel,
        notificationViewModel = notificationViewModel,
        adminViewModel = adminViewModel
    )

    // Navigation bar visibility
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val hideNavbar = currentRoute?.let {
        it in listOf(
            Screen.Login.route,
            Screen.Register.route,
            Screen.ForgotPassword.route,
            Screen.ChangePassword.route + "?email={email}",
            Screen.Setting.route,
            Screen.BookDetail.route,
            Screen.Cart.route,
            Screen.HistoryMember.route
        )
    } ?: false

    val hideTopBar = currentRoute?.let {
        it in listOf(
            Screen.HistoryMember.route
        )
    } ?: false

    // Main UI Structure
    Scaffold(
        bottomBar = {
            if (!hideNavbar && isLoggedIn) {
                NavigationBar().Navbar(navController)
            }
        }, topBar = {
            val logoutState = authViewModel.logOutState.collectAsState()
            LaunchedEffect(logoutState.value) {
                when (logoutState.value) {
                    is Result.Success -> {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }

                    is Result.Error -> {
                        CustomToast().showToast(
                            context,
                            "Gagal logout: ${(logoutState.value as Result.Error).message}",
                            Toast.LENGTH_SHORT
                        )
                    }

                    else -> {}
                }
            }
            if (role == "ADMIN" && !hideTopBar && isLoggedIn) {
                MemberDataHeader {
                    authViewModel.logout(context)
                }
            }
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            startDestination = Screen.Home.route,
            authViewModel = viewModels.authViewModel,
            bookViewModel = viewModels.bookViewModel,
            memberViewModel = viewModels.memberViewModel,
            cartViewModel = viewModels.cartViewModel,
            historyViewModel = viewModels.historyViewModel,
            reservationViewModel = viewModels.reservationViewModel,
            notificationViewModel = viewModels.notificationViewModel,
            adminViewModel = viewModels.adminViewModel,
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
