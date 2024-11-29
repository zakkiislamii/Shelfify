package shelfify.data

import com.example.shelfify.R
import shelfify.be.domain.models.Book

data class BookUI(
    val bookId: Int = 0,
    val title: String,
    val writer: String,
    val stock: Int,
    val bookImage: Int,
    val category: String,
)

fun Book.toBookUI() = BookUI(
    bookId = bookId,
    title = title,
    writer = writer,
    stock = stock,
    bookImage = if (bookImage?.startsWith("http") == true) {
        R.drawable.ic_launcher_background
    } else {
        bookImage?.toInt() ?: R.drawable.ic_launcher_background
    },
    category = category
)
