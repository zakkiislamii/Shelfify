package shelfify.data.dataMapping

import shelfify.contracts.enumerations.Status
import java.util.Date

data class NotificationWithBooksAndReservation(
    val notificationId: Int,
    val userId: Int,
    val reservationId: Int,
    val message: String,
    val createdAt: Date,
    val bookTitles: String,
    val reservationStatus: Status,
)
