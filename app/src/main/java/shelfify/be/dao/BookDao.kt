package shelfify.be.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import shelfify.be.domain.models.Book

@Dao
interface BookDao {

    // Insert a new book (Create)
    @Insert
    suspend fun insertBook(book: Book)

    // Update an existing book
    @Update
    suspend fun updateBook(book: Book)

    // Delete a book
    @Delete
    suspend fun deleteBook(book: Book)

    // Get all books
    @Query("SELECT * FROM Books")
    fun getAllBooks(): Flow<List<Book>>

    // Get books by category
    @Query("SELECT * FROM Books WHERE category = :category")
    fun getBooksByCategory(category: String): Flow<List<Book>>

    @Query("SELECT * FROM Books WHERE book_id = :id")
    fun getBookById(id: Int): Flow<Book?>
}