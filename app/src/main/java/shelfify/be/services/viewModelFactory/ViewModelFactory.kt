package shelfify.be.services.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.storage.FirebaseStorage
import shelfify.be.domain.repositories.BookRepository
import shelfify.be.domain.repositories.CartRepository
import shelfify.be.domain.repositories.HistoryRepository
import shelfify.be.domain.repositories.NotificationRepository
import shelfify.be.domain.repositories.ReservationRepository
import shelfify.be.domain.repositories.UserRepository
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.be.services.viewModel.BookViewModel
import shelfify.be.services.viewModel.CartViewModel
import shelfify.be.services.viewModel.HistoryViewModel
import shelfify.be.services.viewModel.MemberViewModel
import shelfify.be.services.viewModel.NotificationViewModel
import shelfify.be.services.viewModel.ReservationViewModel

class ViewModelFactory(
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val cartRepository: CartRepository,
    private val notificationRepository: NotificationRepository,
    private val reservationRepository: ReservationRepository,
    private val historyRepository: HistoryRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(userRepository) as T
        }
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            return BookViewModel(bookRepository) as T
        }
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(historyRepository) as T
        }
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(cartRepository, bookRepository) as T
        }
        if (modelClass.isAssignableFrom(ReservationViewModel::class.java)) {
            return ReservationViewModel(
                reservationRepository,
                notificationRepository,
                cartRepository,
                bookRepository,
                historyRepository
            ) as T
        }
        if (modelClass.isAssignableFrom(MemberViewModel::class.java)) {
            return MemberViewModel(
                userRepository = userRepository,
                storage = FirebaseStorage.getInstance()
            ) as T
        }
        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            return NotificationViewModel(notificationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}