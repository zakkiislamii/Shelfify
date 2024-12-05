package shelfify.data.dataMapping

import shelfify.be.domain.models.Book
import shelfify.data.baseUI.BookDataBaseUI

data class FavBooks(
    override val bookId: Int,
    val title: String,
    val writer: String,
    val category: String,
    val bookImage: String?,
    val totalReservations: Int,
) : BookDataBaseUI

fun Book.FavBooks(totalReservations: Int) = FavBooks(
    bookId = bookId,
    title = title,
    writer = writer,
    bookImage = if (bookImage?.startsWith("http") == true &&
        (bookImage.endsWith(".jpg", true) ||
                bookImage.endsWith(".jpeg", true) ||
                bookImage.endsWith(".png", true))
    ) {
        bookImage
    } else {
        null
    },
    category = category,
    totalReservations = totalReservations
)
