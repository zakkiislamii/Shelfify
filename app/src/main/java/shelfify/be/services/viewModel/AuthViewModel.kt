package shelfify.be.services.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import shelfify.be.domain.models.User
import shelfify.be.domain.repositories.UserRepository
import shelfify.utils.response.Result

class AuthViewModel(private val userRepository: UserRepository) :
    ViewModel() {

    private val _loginState = MutableStateFlow<Result<User>>(Result.Loading)
    val loginState: StateFlow<Result<User>> = _loginState
    fun login(email: String, password: String, context: Context) {
        viewModelScope.launch {
            _loginState.value = Result.Loading
            try {
                userRepository.login(email, password).fold(
                    onSuccess = { user ->
                        updateLoginSession(
                            context = context,
                            user = user,
                            isLoggedIn = true
                        )
                        _loginState.value = Result.Success(user)
                    },
                    onFailure = { exception ->
                        _loginState.value =
                            Result.Error(exception.message ?: "Unknown error occurred")
                    }
                )
            } catch (e: Exception) {
                _loginState.value = Result.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    private val _registerState = MutableStateFlow<Result<User>>(Result.Loading)
    val registerState: StateFlow<Result<User>> = _registerState
    fun register(
        fullName: String,
        email: String,
        phoneNumber: String,
        address: String,
        faculty: String,
        password: String,
    ) {
        viewModelScope.launch {
            _registerState.value = Result.Loading
            try {
                userRepository.register(
                    fullName,
                    email,
                    phoneNumber,
                    address,
                    faculty,
                    password
                ).fold(
                    onSuccess = { user ->
                        _registerState.value = Result.Success(user)
                    },
                    onFailure = { exception ->
                        _registerState.value =
                            Result.Error(exception.message ?: "Unknown error occurred")
                    }
                )
            } catch (e: Exception) {
                _registerState.value = Result.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    private val _getUserByEmailState = MutableStateFlow<Result<User>>(Result.Loading)
    val getUserByEmailState: StateFlow<Result<User>> = _getUserByEmailState
    fun getUserByEmail(email: String) {
        viewModelScope.launch {
            _getUserByEmailState.value = Result.Loading
            try {
                userRepository.getUserByEmail(
                    email
                ).fold(
                    onSuccess = { user ->
                        _getUserByEmailState.value = Result.Success(user)
                    },
                    onFailure = { exception ->
                        _getUserByEmailState.value =
                            Result.Error(exception.message ?: "Unknown error occurred")
                    }
                )
            } catch (e: Exception) {
                _getUserByEmailState.value = Result.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    private val _changePasswordState = MutableStateFlow<Result<User>>(Result.Loading)
    val changePasswordState: StateFlow<Result<User>> = _changePasswordState
    fun changePassword(email: String, confirmPassword: String) {
        viewModelScope.launch {
            _changePasswordState.value = Result.Loading
            try {
                val userResult = userRepository.getUserByEmail(email)
                if (userResult.isFailure) {
                    _changePasswordState.value = Result.Error("User tidak ditemukan")
                    return@launch
                }

                val user = userResult.getOrNull()

                val updatedResult = userRepository.changePassword(
                    user?.userId ?: return@launch,
                    confirmPassword
                )

                if (updatedResult.isSuccess) {
                    _changePasswordState.value =
                        Result.Success(user.copy(password = confirmPassword))
                } else {
                    _changePasswordState.value = Result.Error("Gagal mengganti password")
                }

            } catch (e: Exception) {
                _changePasswordState.value = Result.Error(e.message ?: "Unknown error occurred")
            }
        }
    }


    private val _logOutState = MutableStateFlow<Result<Unit>>(Result.Success(Unit))
    val logOutState: StateFlow<Result<Unit>> = _logOutState.asStateFlow()

    fun logout(context: Context) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                // Clear shared preferences
                withContext(Dispatchers.IO) {
                    clearLoginStatus(context)
                }

                // Reset semua state
                _loginState.value = Result.Loading
                _getUserByEmailState.value = Result.Loading
                // Set success setelah semua clear
                _logOutState.value = Result.Success(Unit)

            } catch (e: Exception) {
                _logOutState.value = Result.Error(e.message ?: "Logout failed")
            }
        }
    }


    private fun updateLoginSession(
        context: Context,
        user: User,
        isLoggedIn: Boolean,
    ) {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            if (isLoggedIn) {
                putBoolean("is_logged_in", true)
                putInt("user_id", user.userId)
                putString("email", user.email)
                putString("role", user.role.name)
                Log.d("AuthViewModel", "User logged in: ${user.email}")
            } else {
                clear()
                Log.d("AuthViewModel", "User session cleared")
            }
            apply()
        }
    }

    private fun clearLoginStatus(context: Context) {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .clear()
            .apply()
    }
}
