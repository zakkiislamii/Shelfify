package shelfify.ui.admin.bookData.components.addBook

import android.net.Uri
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
import shelfify.routers.Screen
import shelfify.ui.admin.bookData.components.addBook.components.AddBookField
import shelfify.ui.admin.bookData.components.addBook.components.AddBookHeader
import shelfify.ui.admin.bookData.components.button.AddBookFieldButton
import shelfify.utils.toast.CustomToast

class ShowAddBookScreen {
    @Composable
    fun AddBook(navController: NavController, adminViewModel: AdminViewModel) {
        val scrollState = rememberScrollState()
        val addBookField = AddBookField()
        val context = LocalContext.current

        // State untuk menyimpan nilai input
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
        var errorMessage by remember { mutableStateOf<String?>(null) }

        Scaffold(
            topBar = {
                AddBookHeader {
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
                        addBookField.TitleField(
                            value = title,
                            onValueChange = { title = it }
                        )

                        addBookField.WriterField(
                            value = writer,
                            onValueChange = { writer = it }
                        )

                        addBookField.IsbnField(
                            value = isbn,
                            onValueChange = { isbn = it }
                        )

                        addBookField.PublisherField(
                            value = publisher,
                            onValueChange = { publisher = it }
                        )

                        addBookField.PublisherYearField(
                            value = publishYear,
                            onValueChange = { publishYear = it }
                        )

                        addBookField.LanguageField(
                            value = language,
                            onValueChange = { language = it }
                        )

                        addBookField.StockField(
                            value = stock,
                            onValueChange = { stock = it }
                        )

                        addBookField.PagesField(
                            value = pages,
                            onValueChange = { pages = it }
                        )

                        addBookField.DescriptionField(
                            value = description,
                            onValueChange = { description = it }
                        )

                        addBookField.CategoryDropdownField(
                            selectedCategory = category,
                            onCategorySelected = { category = it }
                        )

                        addBookField.ImageUrlField(
                            value = imageUrl,
                            onValueChange = { newUrl ->
                                imageUrl = newUrl
                            },
                            onUriSelected = { uri ->
                                selectedImageUri =
                                    uri
                            }
                        )
                        errorMessage?.let {
                            CustomToast().showToast(context, it, Toast.LENGTH_SHORT)
                        }
                    }
                }
            },
            bottomBar = {
                AddBookFieldButton().Button {
                    if (title.isEmpty() || writer.isEmpty() || isbn.isEmpty() ||
                        publisher.isEmpty() || publishYear.isEmpty() || language.isEmpty() ||
                        stock.isEmpty() || pages.isEmpty() || category.isEmpty()
                    ) {
                        errorMessage = "Please fill out all fields."
                        return@Button
                    } else {
                        errorMessage = null
                    }

                    // Convert string ke tipe data yang sesuai
                    val book = Book(
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
                    adminViewModel.insertBook(book, selectedImageUri)
                    navController.navigate(Screen.BookData.route)
                }
            }
        )
    }
}