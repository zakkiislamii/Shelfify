package shelfify.data.dataMapping

import com.example.shelfify.R
import shelfify.be.domain.models.Book

data class BookDetailData(
    val bookId: Int,
    val title: String,
    val writer: String,
    val stock: Int,
    val pages: Int,
    val bookImage: Int,
    val language: String,
    val description: String?,
    val category: String,
)

fun Book.BookDetailData() = BookDetailData(
    bookId = bookId,
    title = title,
    writer = writer,
    language = language,
    pages = pages,
    description = description,
    stock = stock,
    bookImage = if (bookImage?.startsWith("http") == true &&
        (bookImage.endsWith(".jpg", true) ||
                bookImage.endsWith(".jpeg", true) ||
                bookImage.endsWith(".png", true))
    ) {
        R.drawable.ic_launcher_background
    } else {
        bookImage?.toIntOrNull() ?: R.drawable.ic_launcher_background
    },
    category = category
)