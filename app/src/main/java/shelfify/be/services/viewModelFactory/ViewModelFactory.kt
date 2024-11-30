package shelfify.be.services.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.storage.FirebaseStorage
import shelfify.be.domain.repositories.BookRepository
import shelfify.be.domain.repositories.CartRepository
import shelfify.be.domain.repositories.UserRepository
import shelfify.be.services.viewModel.AuthViewModel
import shelfify.be.services.viewModel.BookViewModel
import shelfify.be.services.viewModel.CartViewModel
import shelfify.be.services.viewModel.MemberViewModel

class ViewModelFactory(
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val cartRepository: CartRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(userRepository) as T
        }
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            return BookViewModel(bookRepository) as T
        }
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(cartRepository, bookRepository) as T
        }
        if (modelClass.isAssignableFrom(MemberViewModel::class.java)) {
            return MemberViewModel(
                userRepository = userRepository,
                storage = FirebaseStorage.getInstance()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}