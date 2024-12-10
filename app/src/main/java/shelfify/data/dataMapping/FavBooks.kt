package shelfify.data.dataMapping

import shelfify.data.baseUI.BookDataBaseUI

data class FavBooks(
    override val bookId: Int,
    val title: String,
    val writer: String,
    val stock : Int,
    val category: String,
    val bookImage: String?,
    val totalReservations: Int,
) : BookDataBaseUI