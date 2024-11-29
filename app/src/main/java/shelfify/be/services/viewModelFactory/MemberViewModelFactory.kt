package shelfify.be.services.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.storage.FirebaseStorage
import shelfify.be.domain.repositories.UserRepository
import shelfify.be.services.viewModel.MemberViewModel

class MemberViewModelFactory(
    private val userRepository: UserRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberViewModel::class.java)) {
            return MemberViewModel(
                userRepository = userRepository,
                storage = FirebaseStorage.getInstance()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}