package shelfify.ui.homeScreen.home.book.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import shelfify.be.services.viewModel.BookViewModel
import shelfify.data.BookUI
import shelfify.data.toBookUI
import shelfify.ui.components.BookCard
import shelfify.utils.response.Result

@Composable
fun BookScreenBody(
    category: String,
    bookViewModel: BookViewModel,
) {
    var books by remember { mutableStateOf<List<BookUI>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(category) {
        bookViewModel.getBooksByCategory(category)
        bookViewModel.booksByCategoryState.collect { result ->
            when (result) {
                is Result.Success -> {
                    books = result.data.map { it.toBookUI() }
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
                // Use LazyColumn to display one card per row
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(books) { book ->
                        BookCard(book = book, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}
