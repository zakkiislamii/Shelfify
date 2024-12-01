package shelfify.be.domain.repositoryContract

import kotlinx.coroutines.flow.Flow
import shelfify.be.domain.models.User

interface UserRepositoryContract {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(
        fullName: String,
        email: String,
        phoneNumber: String,
        address: String,
        faculty: String,
        password: String,
    ): Result<User>

    suspend fun deleteUser(userId: Int): Result<Boolean>
    suspend fun getProfile(userId: Int): Result<User>
    fun getAllUsers(): Flow<List<User>>
    fun searchUsers(query: String): Flow<List<User>>
    suspend fun getUserByEmail(email: String): Result<User>
    suspend fun changePassword(
        userId: Int,
        password: String,
    ): Result<Boolean>

    suspend fun updateUser(user: User): Result<User>
    suspend fun logout(userId: Int): Result<Boolean>
}