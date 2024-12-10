package shelfify.ui.member.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import shelfify.be.domain.models.Reservation
import shelfify.be.services.viewModel.BookViewModel
import shelfify.be.services.viewModel.CartViewModel
import shelfify.be.services.viewModel.ReservationViewModel
import shelfify.contracts.enumerations.Status
import shelfify.contracts.session.UserSessionData
import shelfify.routers.Screen
import shelfify.ui.member.cart.components.CartBody
import shelfify.ui.member.cart.components.CartHeader
import shelfify.ui.library.bookDetail.components.buttonBookDetail.ButtonBookDetails
import shelfify.utils.loading.LoadingIndicator
import shelfify.utils.proxy.RealUserSessionData
import shelfify.utils.proxy.UserSessionProxy
import shelfify.utils.response.Result
import shelfify.utils.toast.CustomToast

class ShowCartScreen {
    @Composable
    fun Cart(
        navController: NavController,
        cartViewModel: CartViewModel,
        reservationViewModel: ReservationViewModel,
        bookViewModel: BookViewModel,
    ) {
        val context = LocalContext.current
        val userSessionData: UserSessionData = UserSessionProxy(RealUserSessionData())
        val userSession = userSessionData.getUserSession(context)
        val userId = userSession.userId.toInt()
        val cartsState by cartViewModel.cartsState.collectAsState()
        val scope = rememberCoroutineScope()

        LaunchedEffect(userId) {
            cartViewModel.getCartsByUserId(userId)
        }

        Scaffold(
            topBar = {
                CartHeader {
                    navController.navigate(Screen.Home.route)
                }
            },

            bottomBar = {
                when (val currentState = cartsState) {
                    is Result.Success -> {
                        if (currentState.data.isNotEmpty()) {
                            ButtonBookDetails().ReservationButton {
                                scope.launch {
                                    try {
                                        val reservations = currentState.data.map { cartItem ->
                                            Reservation(
                                                userId = cartItem.userId,
                                                bookId = cartItem.bookId,
                                                status = Status.PENDING
                                            )
                                        }
                                        withContext(Dispatchers.IO) {
                                            reservationViewModel.addReservation(reservations)
                                        }
                                        withContext(Dispatchers.Main) {
                                            CustomToast().showToast(
                                                context = context,
                                                message = "Semua buku berhasil dipesan!"
                                            )
                                        }

                                    } catch (e: Exception) {
                                        withContext(Dispatchers.Main) {
                                            CustomToast().showToast(
                                                context = context,
                                                message = "Terjadi kesalahan: ${e.message}"
                                            )
                                        }
                                    }
                                }

                            }
                        }
                    }

                    else -> {}
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
            ) {
                when (val currentState = cartsState) {
                    is Result.Loading -> {
                        LoadingIndicator()
                    }

                    is Result.Success -> {
                        val cartList = currentState.data
                        CartBody(
                            cartList = cartList,
                            isEmpty = cartList.isEmpty(),
                            cartViewModel = cartViewModel
                        )
                    }

                    is Result.Error -> {
                        Text(
                            text = currentState.message,
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }
}
