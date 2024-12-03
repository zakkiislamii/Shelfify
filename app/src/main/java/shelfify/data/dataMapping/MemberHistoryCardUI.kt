package shelfify.data.dataMapping

import shelfify.contracts.enumerations.Status
import shelfify.data.baseUI.MemberDataBaseUI
import java.util.Date

data class MemberHistoryCardUI(
    override val userId: Int,
    val reservationStatus: Status,
    val totalReserve: Int,
    val createdAt: Date,
    val bookTitles: String,
) : MemberDataBaseUI
