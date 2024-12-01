package shelfify.be.services.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import shelfify.be.domain.models.Notification
import shelfify.be.domain.models.Reservation
import shelfify.be.domain.repositories.BookRepository
import shelfify.be.domain.repositories.CartRepository
import shelfify.be.domain.repositories.NotificationRepository
import shelfify.be.domain.repositories.ReservationRepository
import shelfify.contracts.enumerations.Status
import shelfify.utils.response.Result

class ReservationViewModel(
    private val reservationRepository: ReservationRepository,
    private val notificationRepository: NotificationRepository,
    private val cartRepository: CartRepository,
    private val bookRepository: BookRepository,
) : ViewModel() {

    private val _reservations = MutableStateFlow<List<Reservation>>(emptyList())
    val reservations: StateFlow<List<Reservation>> = _reservations

    private val _addReservationState = MutableStateFlow<Result<Unit>>(Result.Success(Unit))
    val addReservationState: StateFlow<Result<Unit>> = _addReservationState

    // Get reservations by userId
    fun getReservationsByUserId(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                reservationRepository.getReservationsByUserId(userId).collect { reservationsList ->
                    _reservations.value = reservationsList
                }
            } catch (e: Exception) {
                Log.e("ReservationViewModel", "Error getting reservations: ${e.message}", e)
            }
        }
    }

    // Add reservation from book detail
    suspend fun addReservationFromBookDetail(bookId: Int, reservation: Reservation) {
        try {
            val book = bookRepository.getBookById(bookId) ?: throw Exception("Book not found")
            val reservationId = reservationRepository.addReservationSingle(bookId, reservation)
            val notification = Notification(
                userId = reservation.userId,
                reservationId = reservationId.toInt(),
                message = getNotificationMessage(Status.PENDING, book.title)
            )
            notificationRepository.addNotification(notification)
        } catch (e: Exception) {
            Log.e("ReservationViewModel", "Error adding reservation: ${e.message}", e)
            _addReservationState.value = Result.Error(e.message ?: "Failed to add reservation")
            throw e
        }
    }


    // Add multiple reservations
    suspend fun addReservation(reservations: List<Reservation>) {
        try {
            _addReservationState.value = Result.Loading
            val reservationIds = mutableListOf<Long>()

            val asyncTasks = reservations.map { reservation ->
                viewModelScope.async(Dispatchers.IO) {
                    try {
                        val id = reservationRepository.insertReservation(reservation)
                        reservationIds.add(id)

                        val book = bookRepository.getBookById(reservation.bookId)
                        val bookTitle = book?.title ?: "Unknown Book"

                        val notification = Notification(
                            userId = reservation.userId,
                            reservationId = id.toInt(),
                            message = getNotificationMessage(Status.PENDING, bookTitle)
                        )
                        notificationRepository.addNotification(notification)

                        cartRepository.deleteCartAfterReserve(bookId = reservation.bookId)

                    } catch (e: Exception) {
                        Log.e(
                            "ReservationViewModel",
                            "Error adding reservation for bookId ${reservation.bookId}: ${e.message}",
                            e
                        )
                    }
                }
            }

            asyncTasks.forEach { it.await() }

            if (reservations.isNotEmpty()) {
                getReservationsByUserId(reservations[0].userId)
            }
            _addReservationState.value = Result.Success(Unit)

        } catch (e: Exception) {
            Log.e("ReservationViewModel", "Error adding reservations: ${e.message}", e)
            _addReservationState.value = Result.Error(e.message ?: "Failed to add reservations")
            throw e
        }
    }

    // Generate the notification message based on reservation status
    private fun getNotificationMessage(status: Status, bookTitle: String): String {
        return when (status) {
            Status.PENDING -> "Your reservation for the book '$bookTitle' was successful! Please visit the History to pick up your book."
            Status.REJECTED -> "Your reservation for the book '$bookTitle' has been canceled because the book was not picked up at the History within the specified time."
            Status.BORROWED -> "The book '$bookTitle' has been successfully borrowed! Make sure to return it on time."
            Status.RETURNED -> "The book '$bookTitle' has been returned, have a nice day!"
            else -> "Status unknown for the book '$bookTitle'."
        }
    }
}
