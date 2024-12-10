package shelfify.data.viewModel

import shelfify.be.services.viewModel.AdminViewModel
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.be.services.viewModel.BookViewModel
import shelfify.be.services.viewModel.CartViewModel
import shelfify.be.services.viewModel.HistoryViewModel
import shelfify.be.services.viewModel.MemberViewModel
import shelfify.be.services.viewModel.NotificationViewModel
import shelfify.be.services.viewModel.ReservationViewModel

data class DataViewModel(
    val authViewModel: AuthViewModel,
    val bookViewModel: BookViewModel,
    val memberViewModel: MemberViewModel,
    val cartViewModel: CartViewModel,
    val historyViewModel: HistoryViewModel,
    val reservationViewModel: ReservationViewModel,
    val notificationViewModel: NotificationViewModel,
    val adminViewModel: AdminViewModel,
)