package shelfify.data

import com.example.shelfify.R
import shelfify.be.domain.models.Book

data class BookUI(
    override val bookId: Int,
    val title: String,
    val writer: String,
    val stock: Int,
    val bookImage: Int,
    val category: String,
) : BaseUI

fun Book.BookUI() = BookUI(
    bookId = bookId,
    title = title,
    writer = writer,
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
