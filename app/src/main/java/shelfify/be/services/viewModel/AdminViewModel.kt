package shelfify.be.services.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import shelfify.be.domain.models.User
import shelfify.be.domain.repositories.BookRepository
import shelfify.be.domain.repositories.HistoryRepository
import shelfify.be.domain.repositories.UserRepository
import shelfify.data.dataMapping.BookUI
import shelfify.data.dataMapping.MemberHistoryCardUI
import java.util.Calendar
import java.util.Date

class AdminViewModel(
    private val userRepository: UserRepository,
    private val historyRepository: HistoryRepository,
    private val bookRepository: BookRepository,
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

    // Delete a user
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


    private val _memberHistory = MutableStateFlow<List<MemberHistoryCardUI>>(emptyList())
    val memberHistory: StateFlow<List<MemberHistoryCardUI>> = _memberHistory
    fun getMemberHistory(userId: Int) {
        viewModelScope.launch {
            historyRepository.getMemberHistoryByUserId(userId)
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
                    _memberHistory.value = groupedHistory
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


    private fun truncateToMinutes(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }
}


