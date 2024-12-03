package shelfify.data.dataMapping

import com.example.shelfify.R
import shelfify.be.domain.models.Book
import shelfify.data.baseUI.BookDataBaseUI

data class HistoryBookUI(
    override val bookId: Int,
    val title: String,
    val writer: String,
    val bookImage: Int,
) : BookDataBaseUI

fun Book.HistoryBookUI() = HistoryBookUI(
    bookId = bookId,
    title = title,
    writer = writer,
    bookImage = if (bookImage?.startsWith("http") == true &&
        (bookImage.endsWith(".jpg", true) ||
                bookImage.endsWith(".jpeg", true) ||
                bookImage.endsWith(".png", true))
    ) {
        R.drawable.ic_launcher_background
    } else {
        bookImage?.toIntOrNull() ?: R.drawable.ic_launcher_background
    },
)