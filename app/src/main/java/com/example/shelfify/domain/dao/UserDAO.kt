package com.example.shelfify.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shelfify.domain.models.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User)

    @Query("DELETE FROM Users WHERE user_id = :userId")
    suspend fun deleteUser(userId: Int): Int

    @Query("SELECT * FROM Users WHERE user_id = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT * FROM Users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM Users")
    suspend fun getAllUsers(): List<User>

    @Query(
        """
        SELECT * FROM Users 
        WHERE full_name LIKE :query 
        OR email LIKE :query 
        OR faculty LIKE :query
    """
    )
    suspend fun searchUsers(query: String): List<User>

    @Query("SELECT * FROM Users WHERE faculty = :faculty")
    suspend fun getUsersByFaculty(faculty: String): List<User>
}