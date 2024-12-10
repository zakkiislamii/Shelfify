package shelfify.ui.library.frequentlyBorrowedBooks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import shelfify.be.services.viewModel.AdminViewModel
import shelfify.ui.components.card.FrequentlyBorrowedBooksCard
import shelfify.ui.theme.MainColor

class ShowFrequentlyBorrowedBooksScreen {
    @Composable
    fun FrequentlyBorrowedBooks(
        navController: NavController,
        adminViewModel: AdminViewModel,
    ) {
        // Observe the list of frequently borrowed books
        val favBooks by adminViewModel.bookReservationSummary.collectAsState(initial = emptyList())
        LaunchedEffect(Unit) {
            adminViewModel.getFavBooks()
        }

        Scaffold(
            topBar = {
                Text(text = "Frequently Borrowed Books", color = MainColor)
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        items(favBooks) { favBook ->
                            FrequentlyBorrowedBooksCard().CreateCard(item = favBook) {
                                navController.navigate("bookDetail/${favBook.bookId}")
                            }
                        }
                    }
                }
            },
        )
    }
}