package shelfify.ui.homeScreen.home.bookDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import shelfify.be.services.viewModel.BookViewModel
import shelfify.data.BookDetailData
import shelfify.ui.homeScreen.home.bookDetail.components.BookDetailHeader
import shelfify.ui.homeScreen.home.bookDetail.components.buttonBookDetail.BookDetailBody
import shelfify.utils.response.Result


class ShowBookDetail {
    @Composable
    fun BookDetail(navController: NavController, bookViewModel: BookViewModel) {
        val id = navController.currentBackStackEntry?.arguments?.getInt("id") ?: 0

        // Ambil data buku berdasarkan ID
        LaunchedEffect(id) {
            if (id != 0) {
                bookViewModel.getBookById(id)
            }
        }

        // Ambil state buku berdasarkan ID
        val bookState = bookViewModel.bookByIdState.collectAsState()

        // Menampilkan loading, error, atau data buku
        Scaffold(
            topBar = {
                BookDetailHeader {
                    navController.popBackStack()
                }
            },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                when (val result = bookState.value) {
                    is Result.Success -> {
                        val book = result.data?.BookDetailData()
                        if (book != null) {
                            BookDetailBody(book = book)
                        } else {

                        }
                    }

                    is Result.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is Result.Error -> {

                    }


                }
            }
        }
    }
}
