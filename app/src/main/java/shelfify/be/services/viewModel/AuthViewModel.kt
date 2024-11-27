package shelfify.be.services.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import shelfify.be.domain.models.User
import shelfify.be.domain.repositories.UserRepository
import shelfify.utils.response.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository, private val context: Context) :
    ViewModel() {

    private val _loginState = MutableStateFlow<Result<User>>(Result.Loading)
    val loginState: StateFlow<Result<User>> = _loginState
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = Result.Loading
            try {
                userRepository.login(email, password).fold(
                    onSuccess = { user ->
                        _loginState.value = Result.Success(user)
                        saveLoginStatus(
                            context,
                            true,
                            user.userId,
                            user.email,
                            user.role.name
                        )
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

    private val _forgotPasswordState = MutableStateFlow<Result<User>>(Result.Loading)
    val forgotPasswordState: StateFlow<Result<User>> = _forgotPasswordState
    fun getUserByEmail(email: String) {
        viewModelScope.launch {
            _forgotPasswordState.value = Result.Loading
            try {
                userRepository.getUserByEmail(
                    email
                ).fold(
                    onSuccess = { user ->
                        _forgotPasswordState.value = Result.Success(user)
                    },
                    onFailure = { exception ->
                        _forgotPasswordState.value =
                            Result.Error(exception.message ?: "Unknown error occurred")
                    }
                )
            } catch (e: Exception) {
                _forgotPasswordState.value = Result.Error(e.message ?: "Unknown error occurred")
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
                    _changePasswordState.value = Result.Success(user.copy(password = confirmPassword))
                } else {
                    _changePasswordState.value = Result.Error("Gagal mengganti password")
                }

            } catch (e: Exception) {
                _changePasswordState.value = Result.Error(e.message ?: "Unknown error occurred")
            }
        }
    }



    private fun saveLoginStatus(
        context: Context,
        isLoggedIn: Boolean,
        userId: Int,
        email: String,
        role: String,
    ) {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", isLoggedIn)
        editor.putInt("user_id", userId)
        editor.putString("email", email)
        editor.putString("role", role)
        editor.apply()
    }
}
