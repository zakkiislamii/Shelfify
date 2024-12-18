package shelfify.ui.library.bookDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import shelfify.be.domain.models.CartEntity
import shelfify.be.domain.models.Reservation
import shelfify.be.services.viewModel.BookViewModel
import shelfify.be.services.viewModel.CartViewModel
import shelfify.be.services.viewModel.ReservationViewModel
import shelfify.contracts.session.UserSessionData
import shelfify.data.dataMapping.BookDetailData
import shelfify.ui.library.bookDetail.components.BookDetailBody
import shelfify.ui.library.bookDetail.components.BookDetailHeader
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
        reservationViewModel: ReservationViewModel,
        id: Int,
    ) {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val userSessionData: UserSessionData = UserSessionProxy(RealUserSessionData())
        val userSession = userSessionData.getUserSession(context)
        val userId = userSession.userId.toInt()
        val bookState = bookViewModel.bookByIdState.collectAsState()
        var hasActiveReservation by remember { mutableStateOf(false) }

        LaunchedEffect(userId) {
            reservationViewModel.checkUserHasActiveReservation(userId).collect { result ->
                hasActiveReservation = when (result) {
                    is Result.Success -> result.data
                    else -> false
                }
            }
        }


        LaunchedEffect(id) {
            if (id != 0) {
                bookViewModel.getBookById(id)
            }
        }

        Scaffold(
            topBar = {
                BookDetailHeader {
                    navController.popBackStack("bookDetail/{id}", inclusive = true)
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
                        val book = result.data?.let {
                            BookDetailData(
                                bookId = it.bookId,
                                title = result.data.title,
                                writer = result.data.writer,
                                stock = result.data.stock,
                                pages = result.data.pages,
                                bookImage = result.data.bookImage,
                                language = result.data.language,
                                description = result.data.description,
                                category = result.data.category
                            )
                        }

                        if (book != null) {
                            BookDetailBody().BookDetail(
                                book = book,
                                onAddToCart = {
                                    if (userId != 0 && !hasActiveReservation) {
                                        scope.launch {
                                            val existsResult =
                                                cartViewModel.isBookExistsInCart(userId, id)
                                            when (existsResult) {
                                                is Result.Success -> {
                                                    val cartEntity = CartEntity(
                                                        userId = userId, bookId = id
                                                    )
                                                    cartViewModel.addCart(cartEntity)
                                                    CustomToast().showToast(
                                                        context = context,
                                                        message = "Buku berhasil ditambahkan ke keranjang"
                                                    )
                                                    bookViewModel.getBookById(id)
                                                }

                                                is Result.Error -> {
                                                    CustomToast().showToast(
                                                        context = context,
                                                        message = existsResult.message
                                                    )
                                                }

                                                is Result.Loading -> {}
                                            }
                                        }
                                    } else {
                                        CustomToast().showToast(
                                            context = context,
                                            message = "Anda memiliki reservasi aktif, tidak dapat menambahkan buku ke keranjang"
                                        )
                                    }
                                },
                                onReserve = {
                                    if (userId != 0 && !hasActiveReservation) {
                                        scope.launch {
                                            try {
                                                reservationViewModel.addReservationFromBookDetail(
                                                    id, Reservation(
                                                        userId = userId, bookId = id
                                                    )
                                                )
                                                CustomToast().showToast(
                                                    context = context,
                                                    message = "Buku berhasil dipesan"
                                                )
                                                bookViewModel.getBookById(id)
                                            } catch (e: Exception) {
                                                CustomToast().showToast(
                                                    context = context,
                                                    message = "Gagal memesan buku: ${e.message}"
                                                )
                                            }
                                        }
                                    } else {
                                        CustomToast().showToast(
                                            context = context,
                                            message = "Anda memiliki reservasi aktif, tidak dapat memesan buku"
                                        )
                                    }
                                },
                                navController = navController,
                                hasActiveReservation = hasActiveReservation
                            )
                        }
                    }

                    is Result.Loading -> {
                        LoadingIndicator()
                    }

                    is Result.Error -> {
                        CustomToast().showToast(
                            context = context, message = "Gagal memuat detail buku"
                        )
                    }
                }
            }
        }
    }
}