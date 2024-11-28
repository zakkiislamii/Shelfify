package shelfify.be.domain.repositories

import shelfify.be.dao.BookDao
import shelfify.be.domain.models.Book

class BookRepository(private val bookDao: BookDao) {

    // Create: Insert new book
    suspend fun insertBook(book: Book) {
        bookDao.insertBook(book)
    }

    // Update: Update existing book
    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }

    // Delete: Delete book
    suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }

    // Read: Get all books
    suspend fun getAllBooks(): List<Book> {
        return bookDao.getAllBooks()
    }

    // Get books by category
    suspend fun getBooksByCategory(category: String): List<Book> {
        return bookDao.getBooksByCategory(category)
    }
}
