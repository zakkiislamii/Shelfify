package shelfify.routers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import shelfify.be.services.viewModel.AdminViewModel
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.be.services.viewModel.BookViewModel
import shelfify.ui.admin.bookData.ShowBookData
import shelfify.ui.admin.bookData.components.addBook.ShowAddBookScreen
import shelfify.ui.admin.bookData.components.editBook.ShowEditBookScreen
import shelfify.ui.admin.favoriteBook.ShowFavoriteData
import shelfify.ui.admin.historyMember.ShowHistoryMemberScreen
import shelfify.ui.admin.reservationData.ShowReservationData
import shelfify.ui.auth.changePassword.ShowChangePasswordScreen
import shelfify.ui.auth.forgotPassword.ShowForgotPasswordScreen
import shelfify.ui.auth.login.ShowLoginScreen
import shelfify.ui.auth.register.ShowRegisterScreen
import shelfify.ui.homeScreen.allCategoryBookScreen.ShowAllCategoryBookScreen
import shelfify.ui.homeScreen.home.ShowHomeScreen
import shelfify.ui.homeScreen.member.cart.ShowCartScreen
import shelfify.ui.homeScreen.member.history.ShowHistoryScreen
import shelfify.ui.homeScreen.member.notification.ShowNotificationScreen
import shelfify.ui.homeScreen.member.profile.ShowProfileScreen
import shelfify.ui.homeScreen.member.setting.ShowSettingScreen
import shelfify.ui.library.book.ShowBookScreen
import shelfify.ui.library.bookDetail.ShowBookDetail


@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    viewModels: DataViewModel,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        authRoutes(navController, viewModels.authViewModel)
        adminRoutes(navController, viewModels.adminViewModel, viewModels.bookViewModel)
        memberRoutes(navController, viewModels)
        commonRoutes(navController, viewModels)
        parameterizedRoutes(navController, viewModels)
    }
}

// Extension function untuk rute autentikasi
private fun NavGraphBuilder.authRoutes(
    navController: NavController,
    authViewModel: AuthViewModel,
) {
    composable(route = Screen.Login.route) {
        ShowLoginScreen().Login(navController, authViewModel)
    }
    composable(route = Screen.Register.route) {
        ShowRegisterScreen().Register(navController, authViewModel)
    }
    composable(route = Screen.ForgotPassword.route) {
        ShowForgotPasswordScreen().ForgotPassword(navController, authViewModel)
    }
}

// Extension function untuk rute admin
private fun NavGraphBuilder.adminRoutes(
    navController: NavController,
    adminViewModel: AdminViewModel,
    bookViewModel: BookViewModel,
) {
    composable(route = Screen.AddBook.route) {
        ShowAddBookScreen().AddBook(navController, adminViewModel)
    }
    composable(route = Screen.BookData.route) {
        ShowBookData().BookData(adminViewModel, navController)
    }
    composable(route = Screen.FavoriteBook.route) {
        ShowFavoriteData().FavoriteData(navController, adminViewModel)
    }
}

// Extension function untuk rute member
private fun NavGraphBuilder.memberRoutes(
    navController: NavController,
    viewModels: DataViewModel,
) {
    composable(route = Screen.Cart.route) {
        ShowCartScreen().Cart(
            navController = navController,
            cartViewModel = viewModels.cartViewModel,
            reservationViewModel = viewModels.reservationViewModel,
            bookViewModel = viewModels.bookViewModel
        )
    }
    composable(route = Screen.ReservationData.route) {
        ShowReservationData().ReservationData()
    }
    composable(route = Screen.History.route) {
        ShowHistoryScreen().HistoryScreen(viewModels.historyViewModel)
    }
}

// Extension function untuk rute umum
private fun NavGraphBuilder.commonRoutes(
    navController: NavController,
    viewModels: DataViewModel,
) {
    composable(route = Screen.Home.route) {
        ShowHomeScreen().Homepage(
            navController = navController,
            authViewModel = viewModels.authViewModel,
            adminViewModel = viewModels.adminViewModel
        )
    }
    composable(route = Screen.SearchScreen.route) {
        ShowAllCategoryBookScreen().AllCategoryBook(navController)
    }
    composable(route = Screen.Notification.route) {
        ShowNotificationScreen().NotificationScreen(viewModels.notificationViewModel)
    }
    composable(route = Screen.Profile.route) {
        ShowProfileScreen().ProfileScreen(
            navController = navController,
            authViewModel = viewModels.authViewModel,
            memberViewModel = viewModels.memberViewModel
        )
    }
    composable(route = Screen.Setting.route) {
        ShowSettingScreen().Setting(navController, viewModels.authViewModel)
    }
}

// Extension function untuk rute dengan parameter
private fun NavGraphBuilder.parameterizedRoutes(
    navController: NavController,
    viewModels: DataViewModel,
) {
    // Book Detail Route
    composable(
        route = Screen.BookDetail.route,
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getInt("id") ?: 0
        ShowBookDetail().BookDetail(
            navController = navController,
            bookViewModel = viewModels.bookViewModel,
            cartViewModel = viewModels.cartViewModel,
            reservationViewModel = viewModels.reservationViewModel,
            id = id
        )
    }

    // Book Screen Route
    composable(
        route = Screen.BookScreen.route,
        arguments = listOf(navArgument("category") { type = NavType.StringType })
    ) { backStackEntry ->
        val category = backStackEntry.arguments?.getString("category") ?: ""
        ShowBookScreen().BookScreen(
            navController = navController,
            bookViewModel = viewModels.bookViewModel,
            category = category
        )
    }

    // Change Password Route
    composable(
        route = Screen.ChangePassword.route,
        arguments = listOf(navArgument("email") { type = NavType.StringType })
    ) { backStackEntry ->
        val email = backStackEntry.arguments?.getString("email") ?: ""
        ShowChangePasswordScreen().ChangePassword(
            navController = navController,
            authViewModel = viewModels.authViewModel,
            email = email
        )
    }

    // Edit Book Route
    composable(
        route = Screen.EditBook.route,
        arguments = listOf(navArgument("bookId") { type = NavType.IntType })
    ) { backStackEntry ->
        val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0
        ShowEditBookScreen().EditData(
            navController = navController,
            adminViewModel = viewModels.adminViewModel,
            bookViewModel = viewModels.bookViewModel,
            bookId = bookId
        )
    }

    // History Member Route
    composable(
        route = Screen.HistoryMember.route,
        arguments = listOf(
            navArgument("userId") { type = NavType.IntType },
            navArgument("fullName") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val userId = backStackEntry.arguments?.getInt("userId") ?: 0
        val fullName = backStackEntry.arguments?.getString("fullName") ?: ""
        ShowHistoryMemberScreen().HistoryMember(
            navController = navController,
            adminViewModel = viewModels.adminViewModel,
            userId = userId,
            fullName = fullName
        )
    }
}