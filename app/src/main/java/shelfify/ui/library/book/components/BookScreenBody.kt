package shelfify.ui.library.book.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import shelfify.be.services.viewModel.BookViewModel
import shelfify.data.dataMapping.BookUI
import shelfify.ui.components.card.BookCard
import shelfify.utils.response.Result

@Composable
fun BookScreenBody(
    category: String,
    bookViewModel: BookViewModel,
    navController: NavController,
) {
    var books by remember { mutableStateOf<List<BookUI>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(category) {
        bookViewModel.getBooksByCategory(category)
        bookViewModel.booksByCategoryState.collect { result ->
            when (result) {
                is Result.Success -> {
                    books = result.data.map { it.BookUI() }
                    loading = false
                }

                is Result.Error -> {
                    error = result.message
                    loading = false
                }

                is Result.Loading -> {
                    loading = true
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        when {
            loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            error != null -> {
                Text(
                    text = error ?: "Unknown error occurred",
                    color = MaterialTheme.colorScheme.error
                )
            }

            books.isEmpty() -> {
                Text(text = "No books found in this category")
            }

            else -> {
                // Filter out books with stock 0
                val availableBooks = books.filter { it.stock > 0 }

                if (availableBooks.isEmpty()) {
                    Text(text = "No books available in this category")
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(availableBooks) { book ->
                            BookCard().CreateCard(item = book, onClick = {
                                navController.navigate("bookDetail/${book.bookId}")
                            })
                        }
                    }
                }
            }
        }
    }
}
