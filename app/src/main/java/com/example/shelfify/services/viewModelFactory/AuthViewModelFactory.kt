package com.example.shelfify.services.viewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shelfify.domain.repositories.UserRepository
import com.example.shelfify.services.viewModel.AuthViewModel

class AuthViewModelFactory(
    private val userRepository: UserRepository,
    private val context: Context,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(userRepository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}