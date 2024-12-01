package shelfify.data

import shelfify.contracts.enumerations.Status
import java.util.Date

data class HistoryWithBooksAndReservation(
    val reservationStatus: Status,
    val bookImage: String?,
    val bookTitles: String,
    val bookWriter: String,
    val bookId: Int,
    val createdAt: Date,
)
