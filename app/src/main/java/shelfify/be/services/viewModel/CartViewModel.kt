package shelfify.be.services.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import shelfify.be.domain.models.CartEntity
import shelfify.be.domain.repositories.BookRepository
import shelfify.be.domain.repositories.CartRepository
import shelfify.data.CartWithBook
import shelfify.utils.response.Result

class CartViewModel(
    private val cartRepository: CartRepository,
    private val bookRepository: BookRepository,
) : ViewModel() {

    private val _addCartState = MutableStateFlow<Result<Unit>>(Result.Success(Unit))
    val addCartState = _addCartState.asStateFlow()
    fun addCart(cart: CartEntity) {
        viewModelScope.launch {
            try {
                _addCartState.value = Result.Loading
                cartRepository.addCart(cart)
                bookRepository.getBookById(cart.bookId)
                _addCartState.value = Result.Success(Unit)
            } catch (e: Exception) {
                _addCartState.value = Result.Error(e.message ?: "Gagal menambahkan ke keranjang")
            }
        }
    }

    suspend fun isBookExistsInCart(userId: Int, bookId: Int): Result<Boolean> {
        return try {
            val exists = cartRepository.isBookExistsInCart(userId, bookId)
            if (exists) {
                Result.Error("Buku sudah ada di keranjang")
            } else {
                Result.Success(false)
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Gagal memeriksa keberadaan buku di keranjang")
        }
    }

    private val _deleteCartState = MutableStateFlow<Result<Unit>>(Result.Success(Unit))
    val deleteCartState = _deleteCartState.asStateFlow()
    fun deleteCart(cartId: Int, bookId: Int, userId: Int) {
        viewModelScope.launch {
            try {
                _deleteCartState.value = Result.Loading
                cartRepository.deleteCart(cartId, bookId)
                _deleteCartState.value = Result.Success(Unit)
                getCartsByUserId(userId)
            } catch (e: Exception) {
                _deleteCartState.value = Result.Error(e.message ?: "Gagal menghapus dari keranjang")
            }
        }
    }


    private val _cartsState = MutableStateFlow<Result<List<CartWithBook>>>(Result.Loading)
    val cartsState: StateFlow<Result<List<CartWithBook>>> = _cartsState.asStateFlow()

    fun getCartsByUserId(userId: Int) {
        viewModelScope.launch {
            try {
                _cartsState.value = Result.Loading
                cartRepository.getCartsWithBooksByUserId(userId)
                    .collect { carts ->
                        _cartsState.value = Result.Success(carts)
                    }
            } catch (e: Exception) {
                _cartsState.value = Result.Error(e.message ?: "Gagal mengambil data keranjang")
            }
        }
    }
}



