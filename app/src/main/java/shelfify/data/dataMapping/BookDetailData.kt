package shelfify.data.dataMapping

import com.example.shelfify.R
import shelfify.be.domain.models.Book

data class BookDetailData(
    val bookId: Int,
    val title: String,
    val writer: String,
    val stock: Int,
    val pages: Int,
    val bookImage: String?,
    val language: String,
    val description: String?,
    val category: String,
)
