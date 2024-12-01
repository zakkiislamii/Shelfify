package shelfify.routers

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.be.services.viewModel.BookViewModel
import shelfify.be.services.viewModel.CartViewModel
import shelfify.be.services.viewModel.HistoryViewModel
import shelfify.be.services.viewModel.MemberViewModel
import shelfify.be.services.viewModel.NotificationViewModel
import shelfify.be.services.viewModel.ReservationViewModel
import shelfify.be.services.viewModelFactory.ViewModelFactory
import shelfify.ui.components.navbar.NavigationBar

@Composable
fun AppNavHost(navController: NavHostController) {
    val context = LocalContext.current
    val isLoggedIn = checkLoginStatus(context)
    val viewModels = setupViewModels(context)
    val startDestination = getStartDestination(isLoggedIn)
    val hideNavbar = shouldHideNavbar(navController)

    Scaffold(
        bottomBar = {
            if (!hideNavbar) {
                NavigationBar().Navbar(navController)
            }
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            startDestination = startDestination,
            authViewModel = viewModels.authViewModel,
            bookViewModel = viewModels.bookViewModel,
            memberViewModel = viewModels.memberViewModel,
            cartViewModel = viewModels.cartViewModel,
            historyViewModel = viewModels.historyViewModel,
            reservationViewModel = viewModels.reservationViewModel,
            notificationViewModel = viewModels.notificationViewModel,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun checkLoginStatus(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("is_logged_in", false)
}

@Composable
private fun setupViewModels(context: Context): DataViewModel {
    val database = ShelfifyDatabase.getInstance(context)
    val viewModelFactory = ViewModelFactory(
        UserRepository(database.userDao()),
        BookRepository(database.bookDao()),
        CartRepository(database.cartDao()),
        NotificationRepository(database.notificationDao()),
        ReservationRepository(database.reservationDao()),
        HistoryRepository(database.historyDao())
    )

    // Inisialisasi ViewModel dengan factory
    val authViewModel: AuthViewModel = viewModel(factory = viewModelFactory)
    val bookViewModel: BookViewModel = viewModel(factory = viewModelFactory)
    val cartViewModel: CartViewModel = viewModel(factory = viewModelFactory)
    val historyViewModel: HistoryViewModel = viewModel(factory = viewModelFactory)
    val reservationViewModel: ReservationViewModel = viewModel(factory = viewModelFactory)
    val notificationViewModel: NotificationViewModel = viewModel(factory = viewModelFactory)
    val memberViewModel: MemberViewModel = viewModel(factory = viewModelFactory)

    // Kembalikan DataViewModel yang berisi semua ViewModel
    return DataViewModel(
        authViewModel = authViewModel,
        bookViewModel = bookViewModel,
        memberViewModel = memberViewModel,
        cartViewModel = cartViewModel,
        historyViewModel = historyViewModel,
        reservationViewModel = reservationViewModel,
        notificationViewModel = notificationViewModel
    )
}


private fun getStartDestination(isLoggedIn: Boolean): String {
    return if (!isLoggedIn) Screen.Login.route else Screen.Home.route
}

@Composable
private fun shouldHideNavbar(navController: NavHostController): Boolean {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    return currentRoute in listOf(
        Screen.Login.route,
        Screen.Register.route,
        Screen.ForgotPassword.route,
        Screen.ChangePassword.route + "?email={email}",
        Screen.Setting.route,
        Screen.BookDetail.route,
        Screen.Cart.route
    )
}

private data class DataViewModel(
    val authViewModel: AuthViewModel,
    val bookViewModel: BookViewModel,
    val memberViewModel: MemberViewModel,
    val cartViewModel: CartViewModel,
    val reservationViewModel: ReservationViewModel,
    val notificationViewModel: NotificationViewModel,
    val historyViewModel: HistoryViewModel,
)
