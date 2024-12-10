package shelfify.be.services.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import shelfify.be.domain.models.History
import shelfify.be.domain.models.Notification
import shelfify.be.domain.models.Reservation
import shelfify.be.domain.repositories.BookRepository
import shelfify.be.domain.repositories.CartRepository
import shelfify.be.domain.repositories.HistoryRepository
import shelfify.be.domain.repositories.NotificationRepository
import shelfify.be.domain.repositories.ReservationRepository
import shelfify.contracts.enumerations.Status
import shelfify.data.dataMapping.MemberReserveCardUI
import shelfify.utils.response.Result

class ReservationViewModel(
    private val reservationRepository: ReservationRepository,
    private val notificationRepository: NotificationRepository,
    private val cartRepository: CartRepository,
    private val bookRepository: BookRepository,
    private val historyRepository: HistoryRepository,
) : ViewModel() {

    private val _reservations = MutableStateFlow<List<Reservation>>(emptyList())
    val reservations: StateFlow<List<Reservation>> = _reservations

    private val _addReservationState = MutableStateFlow<Result<Unit>>(Result.Success(Unit))
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

    suspend fun checkUserHasActiveReservation(userId: Int): Flow<Result<Boolean>> {
        return flow {
            try {
                reservationRepository.getReservationsByUserId(userId)
                    .map { reservations ->
                        val hasActive =
                            reservations.any {
                                it.status == Status.PENDING || it.status == Status.BORROWED
                            }
                        Result.Success(hasActive)
                    }
                    .collect { emit(it) }
            } catch (e: Exception) {
                emit(Result.Error(e.message ?: "Error checking reservations"))
            }
        }
    }

    suspend fun addReservationFromBookDetail(bookId: Int, reservation: Reservation) {
        try {
            val book = bookRepository.getBookById(bookId) ?: throw Exception("Book not found")
            val reservationId = reservationRepository.addReservationSingle(bookId, reservation)
            val notification = Notification(
                userId = reservation.userId,
                reservationId = reservationId.toInt(),
                message = getNotificationMessage(Status.PENDING, book.title),
                statusNotification = Status.PENDING
            )
            val history = History(
                userId = reservation.userId,
                reservationId = reservationId.toInt(),
                bookId = reservation.bookId,
                statusHistory = Status.PENDING
            )
            notificationRepository.addNotification(notification)
            historyRepository.addHistory(history)
        } catch (e: Exception) {
            Log.e("ReservationViewModel", "Error adding reservation: ${e.message}", e)
            _addReservationState.value = Result.Error(e.message ?: "Failed to add reservation")
            throw e
        }
    }

    suspend fun addReservation(reservations: List<Reservation>) {
        try {
            _addReservationState.value = Result.Loading
            val reservationIds = mutableListOf<Long>()
            val bookTitles = mutableListOf<String>()
            reservations.forEach { reservation ->
                val book = bookRepository.getBookById(reservation.bookId)
                book?.title?.let { bookTitles.add(it) }
            }
            val asyncTasks = reservations.map { reservation ->
                viewModelScope.async(Dispatchers.IO) {
                    try {
                        val id = reservationRepository.insertReservation(reservation)
                        reservationIds.add(id)

                        val history = History(
                            userId = reservation.userId,
                            reservationId = id.toInt(),
                            bookId = reservation.bookId,
                            statusHistory = Status.PENDING
                        )
                        historyRepository.addHistory(history)
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
            if (reservationIds.isNotEmpty()) {
                val notification = Notification(
                    userId = reservations[0].userId,
                    reservationId = reservationIds[0].toInt(),
                    message = getNotificationMessage(Status.PENDING, bookTitles),
                    statusNotification = Status.PENDING
                )
                notificationRepository.addNotification(notification)
            }
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

    private fun getNotificationMessage(status: Status, bookTitle: String): String {
        return getNotificationMessage(status, listOf(bookTitle))
    }

    private val _memberReservations = MutableStateFlow<List<MemberReserveCardUI>>(emptyList())
    private var memberReservationsJob: Job? = null
    private fun getMemberReservations() {
        memberReservationsJob?.cancel()
        memberReservationsJob = viewModelScope.launch {
            try {
                reservationRepository.getMemberReservations().collect { reservations ->
                    _memberReservations.value = reservations
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        memberReservationsJob?.cancel()
    }

    private val _updateStatus = MutableStateFlow<Result<Unit>?>(null)
    val updateStatus: StateFlow<Result<Unit>?> = _updateStatus.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun updateReservationStatus(
        userId: Int,
        reservationIds: String,
        newStatus: Status,
        bookTitles: List<String>,
        bookIds: String,
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val reservationIdsList = reservationIds.split(",").map { it.trim().toInt() }
                val bookIdsList = bookIds.split(",").map { it.trim().toInt() }

                if (reservationIdsList.size != bookIdsList.size) {
                    throw IllegalArgumentException("Jumlah reservationIds dan bookIds harus sama.")
                }

                val updateJobs = reservationIdsList.map { reservationId ->
                    async(Dispatchers.IO) {
                        reservationRepository.updateReservationStatus(
                            userId,
                            reservationId,
                            newStatus
                        )
                    }
                }
                updateJobs.awaitAll()
                val notification = Notification(
                    userId = userId,
                    reservationId = reservationIdsList.first(),
                    message = getNotificationMessage(newStatus, bookTitles),
                    statusNotification = newStatus
                )
                notificationRepository.addNotification(notification)
                reservationIdsList.forEachIndexed { index, reservationId ->
                    val history = History(
                        userId = userId,
                        reservationId = reservationId,
                        bookId = bookIdsList[index],
                        statusHistory = newStatus
                    )
                    historyRepository.addHistory(history)
                }
                _updateStatus.value = Result.Success(Unit)
                getMemberReservations()
                delay(100)
                resetUpdateStatus()
            } catch (e: Exception) {
                _updateStatus.value =
                    Result.Error(e.message ?: "Failed to update reservation status")
                delay(100)
                resetUpdateStatus()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getNotificationMessage(status: Status, bookTitles: List<String>): String {
        val formattedTitles = when {
            bookTitles.isEmpty() -> "Unknown Books"
            bookTitles.size == 1 -> "'${bookTitles[0]}'"
            bookTitles.size == 2 -> "'${bookTitles[0]}' and '${bookTitles[1]}'"
            else -> {
                val allExceptLast = bookTitles.dropLast(1).joinToString("', '", "'", "'")
                "$allExceptLast and '${bookTitles.last()}'"
            }
        }
        val isPlural = bookTitles.size > 1
        return when (status) {
            Status.PENDING -> "Your reservation for the book${if (isPlural) "s" else ""} $formattedTitles was successful! Please visit the Library to pick up your book${if (isPlural) "s" else ""}."
            Status.REJECTED -> "Your reservation for the book${if (isPlural) "s" else ""} $formattedTitles has been canceled because the book${if (isPlural) "s were" else " was"} not picked up at the Library within the specified time."
            Status.BORROWED -> "The book${if (isPlural) "s" else ""} $formattedTitles ${if (isPlural) "have" else "has"} been successfully borrowed! Make sure to return ${if (isPlural) "them" else "it"} on time."
            Status.RETURNED -> "The book${if (isPlural) "s" else ""} $formattedTitles ${if (isPlural) "have" else "has"} been returned, have a nice day!"
        }
    }

    fun resetUpdateStatus() {
        _updateStatus.value = null
    }
}
