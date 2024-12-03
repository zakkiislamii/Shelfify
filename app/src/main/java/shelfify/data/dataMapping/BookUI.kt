package shelfify.data.dataMapping

import com.example.shelfify.R
import shelfify.be.domain.models.Book
import shelfify.data.baseUI.BookDataBaseUI

data class BookUI(
    override val bookId: Int,
    val title: String,
    val writer: String,
    val stock: Int,
    val bookImage: Int,
    val category: String,
) : BookDataBaseUI

fun Book.BookUI() = BookUI(
    bookId = bookId,
    title = title,
    writer = writer,
    stock = stock,
    bookImage = if (bookImage?.startsWith("http") == true &&
        (bookImage.endsWith(".jpg", true) || bookImage.endsWith(
            ".jpeg",
            true
        ) || bookImage.endsWith(".png", true))
    ) {
        // Menyimpan URL gambar atau menggunakan fallback
        R.drawable.ic_launcher_background
    } else {
        R.drawable.ic_launcher_background // Gunakan gambar default jika tidak valid
    },
    category = category
)

