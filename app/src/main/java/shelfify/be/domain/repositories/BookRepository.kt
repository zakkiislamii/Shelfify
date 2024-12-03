package shelfify.be.domain.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import shelfify.be.dao.BookDao
import shelfify.be.domain.models.Book
import shelfify.be.domain.repositoryContract.BookRepositoryContract
import shelfify.data.dataMapping.BookUI

class BookRepository(private val bookDao: BookDao) : BookRepositoryContract {
    override suspend fun insertBook(book: Book) {
        bookDao.insertBook(book)
    }

    override suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }

    override suspend fun deleteBook(bookId: Int) {
        bookDao.deleteBook(bookId)
    }

    override suspend fun getAllBooks(): List<Book> {
        return bookDao.getAllBooks().first()
    }

    override suspend fun getBooksByCategory(category: String): List<Book> {
        return bookDao.getBooksByCategory(category).first()
    }

    override suspend fun getBookById(id: Int): Book? {
        return bookDao.getBookById(id).first()
    }

    override suspend fun getAllBooksForUI(): Flow<List<BookUI>> {
        return bookDao.getAllBooksForUI()
    }
}