package shelfify.ui.homeScreen.home.book

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import shelfify.be.services.viewModel.BookViewModel
import shelfify.ui.homeScreen.home.book.components.BookScreenBody
import shelfify.ui.homeScreen.home.book.components.BookScreenHeader

class ShowBookScreen {
    @Composable
    fun BookScreen(
        navController: NavController,
        bookViewModel: BookViewModel,
    ) {
        val category =
            Uri.decode(navController.currentBackStackEntry?.arguments?.getString("category") ?: "")
        Scaffold(
            topBar = {
                BookScreenHeader(
                    category = category,
                    onClick = { navController.popBackStack() })
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(paddingValues),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 10.dp, end = 10.dp, bottom = 0.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BookScreenBody(
                            category = category,
                            bookViewModel = bookViewModel,
                            navController = navController
                        )
                    }
                }
            }
        )
    }
}