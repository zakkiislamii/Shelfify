package shelfify.be.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import shelfify.be.domain.models.Notification
import shelfify.data.dataMapping.NotificationWithBooksAndReservation

@Dao
interface NotificationDao {
    @Transaction
    @Query(
        """
 SELECT 
            n.notification_id AS notificationId,
            n.user_id AS userId,
            n.reservation_id AS reservationId,
            n.message AS message,
            n.created_at AS createdAt,
            GROUP_CONCAT(b.title) AS bookTitles,
            n.status_notification AS reservationStatus 
        FROM Notifications n
        INNER JOIN Reservations r ON n.reservation_id = r.reservation_id
        INNER JOIN Books b ON r.book_id = b.book_id
        WHERE n.user_id = :userId
        GROUP BY n.notification_id  
        ORDER BY n.created_at DESC 
"""
    )
    fun getNotificationsByUserId(userId: Int): Flow<List<NotificationWithBooksAndReservation>>

    @Insert
    suspend fun addNotification(notification: Notification)
}

