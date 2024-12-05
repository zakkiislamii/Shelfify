package shelfify.ui.admin.bookData.components.editBook

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import shelfify.be.domain.models.Book
import shelfify.be.services.viewModel.AdminViewModel
import shelfify.be.services.viewModel.BookViewModel
import shelfify.routers.Screen
import shelfify.ui.admin.bookData.components.editBook.button.EditBookFieldButton
import shelfify.ui.admin.bookData.components.editBook.components.EditBookField
import shelfify.ui.admin.bookData.components.editBook.components.EditBookHeader
import shelfify.utils.response.Result
import shelfify.utils.toast.CustomToast

class ShowEditBookScreen {
    @Composable
    fun EditData(
        navController: NavController,
        adminViewModel: AdminViewModel,
        bookViewModel: BookViewModel,
        bookId: Int,
    ) {
        val scrollState = rememberScrollState()
        val editBookField = EditBookField()
        val context = LocalContext.current
        var title by remember { mutableStateOf("") }
        var writer by remember { mutableStateOf("") }
        var isbn by remember { mutableStateOf("") }
        var publisher by remember { mutableStateOf("") }
        var publishYear by remember { mutableStateOf("") }
        var language by remember { mutableStateOf("") }
        var stock by remember { mutableStateOf("") }
        var pages by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var category by remember { mutableStateOf("") }
        var imageUrl by remember { mutableStateOf("") }
        var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

        val bookState by bookViewModel.bookByIdState.collectAsState()

        LaunchedEffect(bookId) {
            bookViewModel.getBookById(bookId)
            Log.d("EditScreen", "Fetching book with ID: $bookId")
        }


        LaunchedEffect(bookState) {
            when (val state = bookState) {
                is Result.Success -> {
                    state.data?.let { book ->
                        title = book.title
                        writer = book.writer
                        isbn = book.isbn
                        publisher = book.publisher
                        publishYear = book.publicationYear.toString()
                        language = book.language
                        stock = book.stock.toString()
                        pages = book.pages.toString()
                        description = book.description ?: ""
                        category = book.category
                        imageUrl = book.bookImage ?: ""
                    }
                }

                is Result.Error -> {
                    CustomToast().showToast(
                        context,
                        "Error loading book: ${state.message}",
                        Toast.LENGTH_LONG
                    )
                }

                is Result.Loading -> {

                }
            }
        }


        Scaffold(
            topBar = {
                EditBookHeader {
                    navController.navigate(Screen.BookData.route)
                }
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
                            .verticalScroll(scrollState)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        editBookField.TitleField(
                            value = title,
                            onValueChange = { title = it }
                        )

                        editBookField.WriterField(
                            value = writer,
                            onValueChange = { writer = it }
                        )

                        editBookField.IsbnField(
                            value = isbn,
                            onValueChange = { isbn = it }
                        )

                        editBookField.PublisherField(
                            value = publisher,
                            onValueChange = { publisher = it }
                        )

                        editBookField.PublisherYearField(
                            value = publishYear,
                            onValueChange = { publishYear = it }
                        )

                        editBookField.LanguageField(
                            value = language,
                            onValueChange = { language = it }
                        )

                        editBookField.StockField(
                            value = stock,
                            onValueChange = { stock = it }
                        )

                        editBookField.PagesField(
                            value = pages,
                            onValueChange = { pages = it }
                        )

                        editBookField.DescriptionField(
                            value = description,
                            onValueChange = { description = it }
                        )

                        editBookField.CategoryDropdownField(
                            selectedCategory = category,
                            onCategorySelected = { category = it }
                        )

                        editBookField.ImageUrlField(
                            value = imageUrl,
                            onValueChange = { newUrl ->
                                imageUrl = newUrl
                            },
                            onUriSelected = { uri ->
                                selectedImageUri =
                                    uri
                            }
                        )

                    }
                }
            },
            bottomBar = {
                EditBookFieldButton().Button {
                    val updatedBook = Book(
                        bookId = bookId,
                        title = title,
                        writer = writer,
                        isbn = isbn,
                        publisher = publisher,
                        publicationYear = publishYear.toIntOrNull() ?: 2024,
                        language = language,
                        stock = stock.toIntOrNull() ?: 0,
                        pages = pages.toIntOrNull() ?: 0,
                        description = description.takeIf { it.isNotEmpty() },
                        category = category,
                        bookImage = imageUrl.takeIf { it.isNotEmpty() }
                    )
                    adminViewModel.updateBook(updatedBook, selectedImageUri)
                    navController.navigate(Screen.BookData.route)
                }
            }
        )
    }
}