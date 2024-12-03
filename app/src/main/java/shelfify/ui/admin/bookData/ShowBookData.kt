package shelfify.ui.admin.bookData

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import shelfify.be.services.viewModel.AdminViewModel
import shelfify.ui.components.card.BookDataCard


class ShowBookData {
    @Composable
    fun BookData(adminViewModel: AdminViewModel) {
        val books by adminViewModel.books.collectAsState()
        LaunchedEffect(Unit) {
            adminViewModel.getAllBooksForUI()
        }

        Scaffold(
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(paddingValues),
                    contentAlignment = Alignment.TopCenter
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(3.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(books) { book ->
                            BookDataCard().CreateCard(
                                item = book,
                                onUpdateClick = { /* TODO: Implement update */ },
                                onDeleteClick = { adminViewModel.deleteBook(book.bookId) }
                            )
                        }
                    }
                }
            }
        )
    }
}