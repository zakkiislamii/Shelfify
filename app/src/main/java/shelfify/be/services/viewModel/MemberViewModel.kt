package shelfify.be.services.viewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storageMetadata
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import shelfify.be.domain.models.User
import shelfify.be.domain.repositories.UserRepository
import shelfify.utils.response.Result
import java.util.Date

class MemberViewModel(
    private val userRepository: UserRepository,
    private val storage: FirebaseStorage,
) : ViewModel() {
    private val _updateUserState = MutableStateFlow<Result<User>?>(null)
    val updateUserState: StateFlow<Result<User>?> = _updateUserState

    private suspend fun uploadImage(imageUri: Uri, userId: Int): String {
        return try {
            val storageRef = storage.reference
            val filename = "profile_images/${userId}_${System.currentTimeMillis()}.jpg"
            val imageRef = storageRef.child(filename)
            val user = userRepository.getProfile(userId).getOrNull()
            user?.profileImage?.let { oldImageUrl ->
                try {
                    val oldImageRef = storage.getReferenceFromUrl(oldImageUrl)
                    oldImageRef.delete().await()
                } catch (e: Exception) {
                    Log.w("MemberViewModel", "Failed to delete old image: ${e.message}")
                }
            }

            val metadata = storageMetadata {
                contentType = "image/jpeg"
            }

            val uploadTask = imageRef.putFile(imageUri, metadata).await()
            imageRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            Log.e("MemberViewModel", "Upload failed", e)
            throw Exception("Gagal mengunggah gambar: ${e.message}")
        }
    }

    fun updateUser(
        userId: Int,
        fullName: String,
        email: String,
        phoneNumber: String,
        address: String,
        faculty: String,
        newImageUri: Uri? = null,
    ) {
        viewModelScope.launch {
            _updateUserState.value = Result.Loading
            try {
                // Get current user
                val userResult = userRepository.getProfile(userId)
                if (userResult.isFailure) {
                    _updateUserState.value = Result.Error("User tidak ditemukan")
                    delay(100)
                    resetUpdateProfileState()
                    return@launch
                }
                val existingUser = userResult.getOrNull() ?: return@launch
                val profileImageUrl = if (newImageUri != null) {
                    try {
                        uploadImage(newImageUri, userId)
                    } catch (e: Exception) {
                        _updateUserState.value = Result.Error(e.message ?: "Gagal upload gambar")
                        return@launch
                    }
                } else {
                    existingUser.profileImage
                }
                val updatedUser = existingUser.copy(
                    fullName = fullName,
                    email = email,
                    phoneNumber = phoneNumber,
                    address = address,
                    faculty = faculty,
                    profileImage = profileImageUrl,
                    updatedAt = Date()
                )

                userRepository.updateUser(updatedUser).fold(
                    onSuccess = {
                        _updateUserState.value = Result.Success(it)
                        delay(100)
                        resetUpdateProfileState()
                    },
                    onFailure = { e ->
                        _updateUserState.value = Result.Error(e.message ?: "Update gagal")
                        delay(100)
                        resetUpdateProfileState()
                    }
                )
            } catch (e: Exception) {
                _updateUserState.value = Result.Error(e.message ?: "Unknown error")
                delay(100)
                resetUpdateProfileState()
            }
        }
    }

    fun resetUpdateProfileState() {
        _updateUserState.value = null
    }
}