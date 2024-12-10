package shelfify.data.dataMapping

import shelfify.data.baseUI.BookDataBaseUI

data class BookUI(
    override val bookId: Int,
    val title: String,
    val writer: String,
    val stock: Int,
    val bookImage: String?,
    val category: String,
) : BookDataBaseUI



