package shelfify.be.services.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import shelfify.be.domain.models.Book
import shelfify.be.domain.repositories.BookRepository
import shelfify.utils.response.Result

class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {

    private val _booksByCategoryState = MutableStateFlow<Result<List<Book>>>(Result.Loading)
    val booksByCategoryState: StateFlow<Result<List<Book>>> = _booksByCategoryState

    // Function to get books by category
    fun getBooksByCategory(category: String) {
        viewModelScope.launch {
            _booksByCategoryState.value = Result.Loading
            try {
                val books = bookRepository.getBooksByCategory(category)
                _booksByCategoryState.value = Result.Success(books)
            } catch (e: Exception) {
                _booksByCategoryState.value = Result.Error(e.message ?: "Error fetching books")
            }
        }
    }

    private val _bookByIdState = MutableStateFlow<Result<Book?>>(Result.Loading)
    val bookByIdState: StateFlow<Result<Book?>> = _bookByIdState
    // Function to get book by ID
    fun getBookById(id: Int) {
        viewModelScope.launch {
            _bookByIdState.value = Result.Loading
            try {
                val book = bookRepository.getBookById(id)
                _bookByIdState.value =
                    Result.Success(book)
            } catch (e: Exception) {
                _bookByIdState.value = Result.Error(e.message ?: "Error fetching book")
            }
        }
    }
}
