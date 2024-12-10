package shelfify.be.services.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storageMetadata
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import shelfify.be.domain.models.Book
import shelfify.be.domain.models.User
import shelfify.be.domain.repositories.BookRepository
import shelfify.be.domain.repositories.HistoryRepository
import shelfify.be.domain.repositories.ReservationRepository
import shelfify.be.domain.repositories.UserRepository
import shelfify.data.dataMapping.BookUI
import shelfify.data.dataMapping.FavBooks
import shelfify.data.dataMapping.MemberHistoryCardUI
import shelfify.data.dataMapping.MemberReserveCardUI
import shelfify.utils.response.Result
import java.util.Calendar
import java.util.Date

class AdminViewModel(
    private val userRepository: UserRepository,
    private val reservationRepository: ReservationRepository,
    private val historyRepository: HistoryRepository,
    private val bookRepository: BookRepository,
    private val storage: FirebaseStorage,
) : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users
    fun getAllUsers() {
        viewModelScope.launch {
            try {
                userRepository.getAllUsers().collect { userList ->
                    _users.value = userList
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteUser(userId: Int) {
        viewModelScope.launch {
            try {
                userRepository.deleteUser(userId)
                getAllUsers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteBook(bookId: Int) {
        viewModelScope.launch {
            try {
                bookRepository.deleteBook(bookId)
                getAllBooksForUI()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun uploadImage(imageUri: Uri, bookId: Int): String {
        return try {
            val storageRef = storage.reference
            val filename = "book_images/${bookId}_${System.currentTimeMillis()}.jpg"
            val imageRef = storageRef.child(filename)

            val metadata = storageMetadata {
                contentType = "image/jpeg"
            }

            imageRef.putFile(imageUri, metadata).await()
            imageRef.downloadUrl.await().toString()

        } catch (e: Exception) {
            Log.e("AdminViewModel", "Upload failed", e)
            throw Exception("Gagal mengunggah gambar buku: ${e.message}")
        }
    }

    private val _addBookState = MutableStateFlow<Result<Unit>>(Result.Loading)
    fun insertBook(book: Book, imageUri: Uri?) {
        viewModelScope.launch {
            _addBookState.value = Result.Loading
            try {
                val bookImageUrl = if (imageUri != null) {
                    uploadImage(imageUri, book.bookId)
                } else {
                    null
                }

                val bookWithImage = book.copy(
                    bookImage = bookImageUrl
                )

                bookRepository.insertBook(bookWithImage)
                _addBookState.value = Result.Success(Unit)

            } catch (e: Exception) {
                _addBookState.value = Result.Error(e.message ?: "Unknown error occurred")
            }
        }
    }


    fun updateBook(book: Book, imageUri: Uri?) {
        viewModelScope.launch {
            _addBookState.value = Result.Loading
            try {
                val bookImageUrl = if (imageUri != null) {
                    uploadImage(imageUri, book.bookId)
                } else {
                    null
                }
                val bookWithImage = book.copy(
                    bookImage = bookImageUrl
                )

                bookRepository.updateBook(bookWithImage)
                _addBookState.value = Result.Success(Unit)

            } catch (e: Exception) {
                _addBookState.value = Result.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    private val _memberHistoryForAdmin = MutableStateFlow<List<MemberHistoryCardUI>>(emptyList())
    val memberHistoryForAdmin: StateFlow<List<MemberHistoryCardUI>> = _memberHistoryForAdmin
    fun getMemberHistoryForAdmin(userId: Int) {
        viewModelScope.launch {
            historyRepository.getMemberHistoryByUserIdForAdmin(userId)
                .collect { history ->
                    val groupedHistory = history
                        .groupBy { it.reservationStatus }
                        .flatMap { (status, statusItems) ->
                            statusItems
                                .groupBy { truncateToMinutes(it.createdAt) }
                                .map { (date, dateItems) ->
                                    MemberHistoryCardUI(
                                        userId = userId,
                                        reservationStatus = status,
                                        totalReserve = dateItems.sumOf { it.totalReserve },
                                        bookTitles = dateItems
                                            .flatMap { it.bookTitles.split(",") }
                                            .distinct()
                                            .joinToString(","),
                                        createdAt = date
                                    )
                                }
                        }
                    _memberHistoryForAdmin.value = groupedHistory
                }
        }
    }

    private val _books = MutableStateFlow<List<BookUI>>(emptyList())
    val books: StateFlow<List<BookUI>> = _books
    fun getAllBooksForUI() {
        viewModelScope.launch {
            try {
                bookRepository.getAllBooksForUI().collect { bookList ->
                    _books.value = bookList
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val _bookReservationSummary =
        MutableStateFlow<List<FavBooks>>(emptyList())
    val bookReservationSummary: StateFlow<List<FavBooks>> = _bookReservationSummary
    fun getFavBooks() {
        viewModelScope.launch {
            try {
                reservationRepository.getFavBooks().collect { bookList ->
                    _bookReservationSummary.value = bookList
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val _memberReservations = MutableStateFlow<List<MemberReserveCardUI>>(emptyList())
    val memberReservations: StateFlow<List<MemberReserveCardUI>> = _memberReservations
    fun getMemberReservations() {
        viewModelScope.launch {
            try {
                reservationRepository.getMemberReservations().collect { reservations ->
                    _memberReservations.value = reservations
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun truncateToMinutes(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }
}


