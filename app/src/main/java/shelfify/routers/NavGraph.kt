package shelfify.routers

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import shelfify.data.viewModel.DataViewModel
import shelfify.ui.admin.bookData.ShowBookData
import shelfify.ui.admin.bookData.components.addBook.ShowAddBookScreen
import shelfify.ui.admin.bookData.components.editBook.ShowEditBookScreen
import shelfify.ui.admin.favoriteBook.ShowFavoriteData
import shelfify.ui.admin.historyMember.ShowHistoryMemberScreen
import shelfify.ui.admin.reservationData.ShowReservationData
import shelfify.ui.admin.reservationData.components.viewDetailsReserve.ShowViewDetailsReserveScreen
import shelfify.ui.auth.changePassword.ShowChangePasswordScreen
import shelfify.ui.auth.forgotPassword.ShowForgotPasswordScreen
import shelfify.ui.auth.login.ShowLoginScreen
import shelfify.ui.auth.register.ShowRegisterScreen
import shelfify.ui.homeScreen.allCategoryBookScreen.ShowAllCategoryBookScreen
import shelfify.ui.homeScreen.home.ShowHomeScreen
import shelfify.ui.library.book.ShowBookScreen
import shelfify.ui.library.bookDetail.ShowBookDetail
import shelfify.ui.member.cart.ShowCartScreen
import shelfify.ui.member.history.ShowHistoryScreen
import shelfify.ui.member.notification.ShowNotificationScreen
import shelfify.ui.member.profile.ShowProfileScreen
import shelfify.ui.member.setting.ShowSettingScreen

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
        composable(
            route = Screen.Auth.Login.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            }
        ) {
            ShowLoginScreen().Login(navController, viewModels.authViewModel)
        }

        composable(route = Screen.Auth.Register.route) {
            ShowRegisterScreen().Register(navController, viewModels.authViewModel)
        }
        composable(route = Screen.Auth.ForgotPassword.route) {
            ShowForgotPasswordScreen().ForgotPassword(navController, viewModels.authViewModel)
        }

        composable(route = Screen.Admin.AddBook.route) {
            ShowAddBookScreen().AddBook(navController, viewModels.adminViewModel)
        }
        composable(route = Screen.Admin.BookData.route) {
            ShowBookData().BookData(viewModels.adminViewModel, navController)
        }
        composable(route = Screen.Admin.FavoriteBook.route) {
            ShowFavoriteData().FavoriteData(navController, viewModels.adminViewModel)
        }
        composable(route = Screen.Admin.ReservationData.route) {
            ShowReservationData().ReservationData(
                navController = navController,
                adminViewModel = viewModels.adminViewModel,
                reservationViewModel = viewModels.reservationViewModel
            )
        }

        composable(route = Screen.Member.Cart.route) {
            ShowCartScreen().Cart(
                navController = navController,
                cartViewModel = viewModels.cartViewModel,
                reservationViewModel = viewModels.reservationViewModel,
                bookViewModel = viewModels.bookViewModel
            )
        }

        composable(route = Screen.Member.History.route) {
            ShowHistoryScreen().HistoryScreen(
                navController = navController,
                viewModels.historyViewModel
            )
        }

        composable(route = Screen.Home.route) {
            ShowHomeScreen().Homepage(
                navController = navController,
                authViewModel = viewModels.authViewModel,
                adminViewModel = viewModels.adminViewModel
            )
        }

        composable(route = Screen.Member.SearchScreen.route) {
            ShowAllCategoryBookScreen().AllCategoryBook(navController)
        }

        composable(route = Screen.Member.Notification.route) {
            ShowNotificationScreen().NotificationScreen(viewModels.notificationViewModel)
        }

        composable(route = Screen.Member.Profile.route) {
            ShowProfileScreen().ProfileScreen(
                navController = navController,
                authViewModel = viewModels.authViewModel,
                memberViewModel = viewModels.memberViewModel
            )
        }

        composable(route = Screen.Member.Setting.route) {
            ShowSettingScreen().Setting(navController, viewModels.authViewModel)
        }

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

        composable(
            route = Screen.Member.BookScreen.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            ShowBookScreen().BookScreen(
                navController = navController,
                bookViewModel = viewModels.bookViewModel,
                category = category
            )
        }

        composable(
            route = Screen.Auth.ChangePassword.route,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ShowChangePasswordScreen().ChangePassword(
                navController = navController,
                authViewModel = viewModels.authViewModel,
                email = email
            )
        }

        composable(
            route = Screen.Admin.EditBook.route,
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

        composable(
            route = Screen.Admin.ViewDetails.route,
            arguments = listOf(
                navArgument("fullName") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType },
                navArgument("writer") { type = NavType.StringType },
                navArgument("reservationId") { type = NavType.StringType },
                navArgument("bookId") { type = NavType.StringType },
                navArgument("bookImage") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = ""
                },
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val fullName = backStackEntry.arguments?.getString("fullName") ?: ""
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val writer = backStackEntry.arguments?.getString("writer") ?: ""
            val reservationId = backStackEntry.arguments?.getString("reservationId") ?: ""
            val bookImage = backStackEntry.arguments?.getString("bookImage") ?: ""
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
            ShowViewDetailsReserveScreen().ViewDetailsReserve(
                navController = navController,
                reservationViewModel = viewModels.reservationViewModel,
                fullName = fullName,
                title = title,
                writer = writer,
                reservationId = reservationId,
                bookImage = bookImage,
                userId = userId,
                bookId = bookId
            )
        }

        composable(
            route = Screen.Admin.HistoryMember.route,
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
}