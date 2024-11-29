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
import shelfify.be.domain.repositories.UserRepository
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.be.services.viewModel.BookViewModel
import shelfify.be.services.viewModel.MemberViewModel
import shelfify.be.services.viewModelFactory.AuthViewModelFactory
import shelfify.be.services.viewModelFactory.BookViewModelFactory
import shelfify.be.services.viewModelFactory.MemberViewModelFactory
import shelfify.contracts.session.UserSessionData
import shelfify.ui.components.NavigationBar
import shelfify.utils.proxy.RealUserSessionData
import shelfify.utils.proxy.UserSessionProxy

@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    val context = LocalContext.current

    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
    val (authViewModel, bookViewModel, memberViewModel) = setupViewModels(context)
    val userSessionData: UserSessionData = UserSessionProxy(RealUserSessionData())
    val userSession = userSessionData.getUserSession(context)
    val startDestination =
        if (!isLoggedIn) Screen.Login.route else Screen.Home.route
    val noNeedNavbar = noNeedNavbar(navController)

    Scaffold(
        bottomBar = {
            if (!noNeedNavbar) {
                NavigationBar().Navbar(navController)
            }
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            startDestination = startDestination,
            authViewModel = authViewModel,
            bookViewModel = bookViewModel,
            memberViewModel = memberViewModel,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun setupViewModels(context: Context): Triple<AuthViewModel, BookViewModel, MemberViewModel> {
    val database = ShelfifyDatabase.getInstance(context)
    val userRepository = UserRepository(database.userDao())
    val bookRepository = BookRepository(database.bookDao())
    return Triple(
        viewModel(factory = AuthViewModelFactory(userRepository)),
        viewModel(factory = BookViewModelFactory(bookRepository)),
        viewModel(factory = MemberViewModelFactory(userRepository))
    )
}

@Composable
private fun noNeedNavbar(navController: NavHostController): Boolean {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    return currentRoute in listOf(
        Screen.Login.route,
        Screen.Register.route,
        Screen.ForgotPassword.route,
        Screen.ChangePassword.route + "?email={email}",
        Screen.Setting.route
    )
}