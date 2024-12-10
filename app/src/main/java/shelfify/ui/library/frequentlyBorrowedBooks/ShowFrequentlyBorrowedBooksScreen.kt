package shelfify.ui.library.frequentlyBorrowedBooks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import shelfify.be.services.viewModel.AdminViewModel
import shelfify.ui.components.card.FrequentlyBorrowedBooksCard
import shelfify.ui.theme.MainColor
import shelfify.utils.toast.CustomToast

class ShowFrequentlyBorrowedBooksScreen {
    @Composable
    fun FrequentlyBorrowedBooks(
        navController: NavController,
        adminViewModel: AdminViewModel,
    ) {
        val context = LocalContext.current
        val favBooks by adminViewModel.bookReservationSummary.collectAsState(initial = emptyList())
        LaunchedEffect(Unit) {
            adminViewModel.getFavBooks()
        }
        Column {
            Text(
                text = "Frequently Borrowed Books",
                color = MainColor,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 20.dp)
            )

            Box(
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(20.dp)
                ) {
                    items(favBooks) { favBook ->
                        FrequentlyBorrowedBooksCard().CreateCard(item = favBook) {
                            if (favBook.stock > 0) {
                                navController.navigate("bookDetail/${favBook.bookId}")
                            } else {
                                CustomToast().showToast(
                                    context,
                                    "Buku dengan judul ${favBook.title} telah habis!"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}