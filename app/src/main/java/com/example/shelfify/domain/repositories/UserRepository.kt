package com.example.shelfify.domain.repositories

import android.util.Base64
import com.example.shelfify.contracts.enumerations.Role
import com.example.shelfify.domain.dao.UserDao
import com.example.shelfify.domain.models.User
import com.toxicbakery.bcrypt.Bcrypt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date

class UserRepository(private val userDao: UserDao) {
    suspend fun login(email: String, password: String): Result<User> {
        return try {
            val user = userDao.getUserByEmail(email)
            val userPassword = user?.password
            val decodedHash = Base64.decode(userPassword, Base64.NO_WRAP)
            if (user != null && Bcrypt.verify(password, decodedHash)) {
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

    suspend fun register(
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


    suspend fun checkLogin(userId: Int): Result<Boolean> {
        return try {
            val user = userDao.getUserById(userId)
            Result.success(user?.isLoggedIn ?: false)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteUser(userId: Int): Result<Boolean> {
        return try {
            val user = userDao.getUserById(userId)
                ?: return Result.failure(Exception("User tidak ditemukan"))

            val rowsDeleted = userDao.deleteUser(userId)
            Result.success(rowsDeleted > 0)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProfile(userId: Int): Result<User> {
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

    fun getAllUsers(): Flow<List<User>> = flow {
        try {
            emit(userDao.getAllUsers())
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    fun searchUsers(query: String): Flow<List<User>> = flow {
        try {
            emit(userDao.searchUsers("%$query%"))
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    suspend fun getUserByEmail(email: String): Result<User> {
        return try {
            val user = userDao.getUserByEmail(email)
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("User tidak ditemukan"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun changePassword(
        userId: Int,
        oldPassword: String,
        newPassword: String,
    ): Result<Boolean> {
        return try {
            val user = userDao.getUserById(userId)
                ?: return Result.failure(Exception("User tidak ditemukan"))

            if (user.password != oldPassword) {
                return Result.failure(Exception("Password lama tidak sesuai"))
            }

            val updatedUser = user.copy(
                password = newPassword,
                updatedAt = Date()
            )
            userDao.updateUser(updatedUser)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUser(user: User): Result<User> {
        return try {
            userDao.updateUser(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout(userId: Int): Result<Boolean> {
        return try {
            val user = userDao.getUserById(userId)
                ?: return Result.failure(Exception("User tidak ditemukan"))

            val updatedUser = user.copy(
                isLoggedIn = false,
                updatedAt = Date()
            )
            userDao.updateUser(updatedUser)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}