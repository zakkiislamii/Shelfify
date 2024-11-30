package shelfify.ui.library.bookDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import shelfify.be.domain.models.CartEntity
import shelfify.be.services.viewModel.BookViewModel
import shelfify.be.services.viewModel.CartViewModel
import shelfify.contracts.session.UserSessionData
import shelfify.data.BookDetailData
import shelfify.ui.library.bookDetail.components.BookDetailHeader
import shelfify.ui.library.bookDetail.components.buttonBookDetail.BookDetailBody
import shelfify.utils.loading.LoadingIndicator
import shelfify.utils.proxy.RealUserSessionData
import shelfify.utils.proxy.UserSessionProxy
import shelfify.utils.response.Result
import shelfify.utils.toast.CustomToast

class ShowBookDetail {
    @Composable
    fun BookDetail(
        navController: NavController,
        bookViewModel: BookViewModel,
        cartViewModel: CartViewModel,
    ) {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()  // Tambahkan ini
        val id = navController.currentBackStackEntry?.arguments?.getInt("id") ?: 0
        val userSessionData: UserSessionData = UserSessionProxy(RealUserSessionData())
        val userSession = userSessionData.getUserSession(context)
        val userId = userSession.userId.toInt()
        val bookState = bookViewModel.bookByIdState.collectAsState()
        val addCartState = cartViewModel.addCartState.collectAsState()

        LaunchedEffect(id) {
            if (id != 0) {
                bookViewModel.getBookById(id)
            }
        }

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
                            BookDetailBody().BookDetail(
                                book = book,
                                onAddToCart = {
                                    if (userId != 0) {
                                        scope.launch {  // Gunakan scope dari rememberCoroutineScope
                                            // Cek dulu apakah buku sudah ada di cart
                                            val existsResult =
                                                cartViewModel.isBookExistsInCart(userId, id)
                                            when (existsResult) {
                                                is Result.Success -> {
                                                    // Buku belum ada di cart, aman untuk menambahkan
                                                    val cartEntity = CartEntity(
                                                        userId = userId,
                                                        bookId = id
                                                    )
                                                    cartViewModel.addCart(cartEntity)
                                                    CustomToast().showToast(
                                                        context = context,
                                                        message = "Buku berhasil ditambahkan ke keranjang"
                                                    )
                                                }

                                                is Result.Error -> {
                                                    // Buku sudah ada di cart
                                                    CustomToast().showToast(
                                                        context = context,
                                                        message = existsResult.message  // Sesuaikan dengan struktur Result.Error Anda
                                                    )
                                                }

                                                is Result.Loading -> {
                                                    // Handle loading state if needed
                                                }
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }

                    is Result.Loading -> {
                        LoadingIndicator()
                    }

                    is Result.Error -> {
                        CustomToast().showToast(
                            context = context,
                            message = "Gagal memuat detail buku"
                        )
                    }
                }
            }
        }
    }
}