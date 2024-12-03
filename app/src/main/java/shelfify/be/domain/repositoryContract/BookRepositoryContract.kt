package shelfify.be.domain.repositoryContract

import kotlinx.coroutines.flow.Flow
import shelfify.be.domain.models.Book
import shelfify.data.dataMapping.BookUI

interface BookRepositoryContract {
    suspend fun insertBook(book: Book)
    suspend fun updateBook(book: Book)
    suspend fun deleteBook(bookId: Int)
    suspend fun getAllBooks(): List<Book>
    suspend fun getBooksByCategory(category: String): List<Book>
    suspend fun getBookById(id: Int): Book?
    suspend fun getAllBooksForUI(): Flow<List<BookUI>>
}