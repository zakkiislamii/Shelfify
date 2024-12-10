package shelfify.data.dataMapping

import shelfify.contracts.enumerations.Status
import shelfify.data.baseUI.MemberDataBaseUI
import java.util.Date

data class MemberReserveCardUI(
    override val userId: Int,
    val reservationId: String,
    val bookId: String,
    val fullName: String,
    val writer: String,
    val bookImage: String?,
    val title: String,
    val reservationStatus: Status,
    val totalReserve: Int,
    val createdAt: Date,
    val bookTitles: String,
) : MemberDataBaseUI