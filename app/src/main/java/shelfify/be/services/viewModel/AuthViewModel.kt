package shelfify.be.services.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
                        updateLoginSession(context, user, true)
                        _loginState.value = Result.Success(user)
                    },
                    onFailure = { exception ->
                        _loginState.value = Result.Error(exception.message ?: "Unknown error")
                    }
                )
            } catch (e: Exception) {
                _loginState.value = Result.Error(e.message ?: "Unknown error")
            }
        }
    }

    private val _logoutState = MutableStateFlow<Result<Unit>>(Result.Loading)
    val logoutState: StateFlow<Result<Unit>> = _logoutState
    fun logout(context: Context) {
        viewModelScope.launch {
            _logoutState.value = Result.Loading
            try {
                _loginState.value = Result.Loading
                _registerState.value = Result.Loading
                _getUserByEmailState.value = Result.Loading
                _changePasswordState.value = Result.Loading
                _logoutState.value = Result.Success(Unit)
            } catch (e: Exception) {
                _logoutState.value = Result.Error(e.message ?: "Unknown error occurred")
            }
        }
    }


    private fun updateLoginSession(context: Context, user: User, isLoggedIn: Boolean) {
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
}
