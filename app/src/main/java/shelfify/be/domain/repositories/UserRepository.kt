package shelfify.be.domain.repositories

import android.util.Base64
import com.toxicbakery.bcrypt.Bcrypt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import shelfify.be.dao.UserDao
import shelfify.be.domain.models.User
import shelfify.be.domain.repositoryContract.UserRepositoryContract
import shelfify.contracts.enumerations.Role
import java.util.Date
import java.util.Locale

class UserRepository(private val userDao: UserDao) : UserRepositoryContract {
    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val user = userDao.getUserByEmail(email)
                ?: return Result.failure(Exception("User tidak ditemukan"))
            val userPassword = user.password
            val decodedHash = Base64.decode(userPassword, Base64.NO_WRAP)
            if (Bcrypt.verify(password, decodedHash)) {
                val updatedUser = user.copy(
                    isLoggedIn = true,
                    updatedAt = Date()
                )
                userDao.updateUser(updatedUser)
                Result.success(updatedUser)
            } else {
                Result.failure(Exception("Email atau password salah"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(
        fullName: String,
        email: String,
        phoneNumber: String,
        address: String,
        faculty: String,
        password: String,
    ): Result<User> {
        return try {
            val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
            if (!emailRegex.matches(email)) {
                return Result.failure(Exception("Email tidak valid"))
            }

            if (password.length < 8) {
                return Result.failure(Exception("Password harus terdiri dari minimal 8 karakter"))
            }

            if (userDao.getUserByEmail(email) != null) {
                return Result.failure(Exception("Email sudah terdaftar"))
            }

            val hashedPassword = Bcrypt.hash(password, 10)
            val hashedPasswordString = Base64.encodeToString(hashedPassword, Base64.NO_WRAP)
            val newUser = User(
                fullName = fullName,
                email = email,
                phoneNumber = phoneNumber,
                address = address,
                faculty = faculty,
                password = hashedPasswordString,
                role = Role.MEMBER,
                createdAt = Date(),
                updatedAt = Date(),
                isLoggedIn = false
            )
            val userId = userDao.insertUser(newUser)
            Result.success(newUser.copy(userId = userId.toInt()))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun deleteUser(userId: Int): Result<Boolean> {
        return try {
            val user = userDao.getUserById(userId)
                ?: return Result.failure(Exception("User tidak ditemukan"))

            val rowsDeleted = userDao.deleteUser(userId)
            Result.success(rowsDeleted > 0)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProfile(userId: Int): Result<User> {
        return try {
            val user = userDao.getUserById(userId)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("User tidak ditemukan"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getAllUsers(): Flow<List<User>> = flow {
        try {
            emit(userDao.getAllUsers())
        } catch (e: Exception) {
            emit(emptyList())
        }
    }


    override suspend fun getUserByEmail(email: String): Result<User> {
        return try {
            val sanitizedEmail = email.trim().lowercase(Locale.getDefault())
            val user = userDao.getUserByEmail(sanitizedEmail)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("User tidak ditemukan"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changePassword(
        userId: Int,
        password: String,
    ): Result<Boolean> {
        return try {
            // Ambil user berdasarkan userId
            val user = userDao.getUserById(userId)
                ?: return Result.failure(Exception("User tidak ditemukan"))

            // Hash password sebelum disimpan
            val hashedPassword = Bcrypt.hash(password, 10)
            val hashedPasswordString = Base64.encodeToString(hashedPassword, Base64.NO_WRAP)

            // Update password
            val updatedUser = user.copy(
                password = hashedPasswordString,
                updatedAt = Date()
            )
            userDao.updateUser(updatedUser)

            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun updateUser(user: User): Result<User> {
        return try {
            userDao.updateUser(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}