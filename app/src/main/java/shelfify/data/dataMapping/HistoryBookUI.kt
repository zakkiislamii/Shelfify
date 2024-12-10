package shelfify.data.dataMapping

import shelfify.data.baseUI.BookDataBaseUI

data class HistoryBookUI(
    override val bookId: Int,
    val title: String,
    val writer: String,
    val bookImage: String?,
) : BookDataBaseUI
