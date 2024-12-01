package shelfify.be.domain.repositoryContract

import shelfify.be.domain.models.Book

interface BookRepositoryContract {
    suspend fun insertBook(book: Book)
    suspend fun updateBook(book: Book)
    suspend fun deleteBook(book: Book)
    suspend fun getAllBooks(): List<Book>
    suspend fun getBooksByCategory(category: String): List<Book>
    suspend fun getBookById(id: Int): Book?
}