package shelfify.ui.layout.member

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import shelfify.be.services.viewModel.AdminViewModel
import shelfify.routers.Screen
import shelfify.ui.homeScreen.home.components.HomeHeader
import shelfify.ui.library.categoryBook.CategoryBook
import shelfify.ui.library.frequentlyBorrowedBooks.ShowFrequentlyBorrowedBooksScreen

@Composable
fun MemberContent(
    firstName: String,
    navController: NavController,
    adminViewModel: AdminViewModel,
    scrollState: LazyListState,
    isScrolled: Boolean,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = !isScrolled,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                HomeHeader(
                    fullname = firstName,
                    onClick = { navController.navigate(Screen.Member.Cart.route) }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            LazyColumn(
                state = scrollState,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    CategoryBook(navController)
                }
                item {
                    ShowFrequentlyBorrowedBooksScreen().FrequentlyBorrowedBooks(
                        navController = navController,
                        adminViewModel = adminViewModel
                    )
                }
            }
        }
    }
}