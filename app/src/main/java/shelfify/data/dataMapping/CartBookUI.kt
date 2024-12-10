package shelfify.data.dataMapping


import com.example.shelfify.R
import shelfify.be.domain.models.Book
import shelfify.data.baseUI.BookDataBaseUI

data class CartBookUI(
    override val bookId: Int,
    val title: String,
    val writer: String,
    val bookImage: String?,
    val category: String,
) : BookDataBaseUI
