package shelfify.ui.admin.reservationData.components.viewDetailsReserve

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import shelfify.be.services.viewModel.ReservationViewModel
import shelfify.contracts.enumerations.Status
import shelfify.ui.admin.reservationData.components.ButtonReserveMember
import shelfify.ui.components.card.ViewReserveCard
import shelfify.utils.loading.LoadingIndicator
import shelfify.utils.response.Result
import shelfify.utils.toast.CustomToast

class ShowViewDetailsReserveScreen {
    @Composable
    fun ViewDetailsReserve(
        navController: NavController,
        reservationViewModel: ReservationViewModel,
        fullName: String,
        title: String,
        writer: String,
        reservationId: String,
        bookImage: String,
        userId: Int,
        bookId: String,
    ) {
        val context = LocalContext.current
        val updateStatus by reservationViewModel.updateStatus.collectAsState()
        val isLoading by reservationViewModel.isLoading.collectAsState()
        val bookTitles = title.split(",").distinct()
        val writerNames = writer.split(",").distinct()
        val bookImages = bookImage.split(",").distinct()
        val reservationIds = reservationId.split(",").distinct()
        val bookIds = bookId.split(",").distinct()
        var isButtonEnabled by remember { mutableStateOf(true) }

        LaunchedEffect(updateStatus) {
            when (updateStatus) {
                is Result.Success -> {
                    isButtonEnabled = true
                    reservationViewModel.resetUpdateStatus()
                    CustomToast().showToast(
                        context,
                        "Reservation updated successfully",
                    )
                    reservationViewModel.resetUpdateStatus()
                    navController.popBackStack()
                }

                is Result.Error -> {
                    isButtonEnabled = true
                    val errorMessage = (updateStatus as Result.Error).message
                    CustomToast().showToast(context, "Error: $errorMessage")
                    reservationViewModel.resetUpdateStatus()
                }

                is Result.Loading -> {
                    // Handle loading state if needed
                }

                null -> {
                    // Initial state, do nothing
                }
            }
        }

        Scaffold(
            topBar = {
                ViewDetailsHeader(fullName = fullName) {
                    navController.popBackStack()
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
                    if (isLoading) {
                        LoadingIndicator()
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 0.dp),
                        contentPadding = PaddingValues(bottom = 16.dp, start = 8.dp, end = 8.dp)
                    ) {
                        items(bookTitles.size) { index ->
                            Box(
                                modifier = Modifier.padding(10.dp)
                            ) {
                                ViewReserveCard().CreateCard(
                                    bookImage = bookImages.getOrElse(index) { "" },
                                    title = bookTitles[index],
                                    writer = writerNames.getOrElse(index) { "" }
                                )
                            }
                        }
                    }
                }
            },
            bottomBar = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Row {
                        ButtonReserveMember().AcceptReservationButton {
                            if (isButtonEnabled) {
                                isButtonEnabled = false
                                if (reservationId.isNotEmpty() && bookId.isNotEmpty()) {
                                    reservationViewModel.updateReservationStatus(
                                        userId = userId,
                                        reservationIds = reservationIds.joinToString(","),
                                        newStatus = Status.BORROWED,
                                        bookTitles = bookTitles,
                                        bookIds = bookIds.joinToString(","),
                                    )
                                } else {
                                    CustomToast().showToast(
                                        context,
                                        "Invalid reservation ID",
                                    )
                                }
                            }
                        }
                        ButtonReserveMember().RejectedButton {
                            if (isButtonEnabled) {
                                isButtonEnabled = false
                                if (reservationId.isNotEmpty() && bookId.isNotEmpty()) {
                                    reservationViewModel.updateReservationStatus(
                                        userId = userId,
                                        reservationIds = reservationIds.joinToString(","),
                                        newStatus = Status.REJECTED,
                                        bookTitles = bookTitles,
                                        bookIds = bookIds.joinToString(","),
                                    )
                                } else {
                                    CustomToast().showToast(
                                        context,
                                        "Invalid reservation ID",
                                    )
                                }
                            }

                        }
                    }

                }
            }
        )
    }
}