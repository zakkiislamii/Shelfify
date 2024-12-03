package shelfify.be.domain.repositoryContract

import kotlinx.coroutines.flow.Flow
import shelfify.be.domain.models.Notification
import shelfify.data.dataMapping.NotificationWithBooksAndReservation

interface NotificationRepositoryContract {
    fun getNotificationsByUserId(userId: Int): Flow<List<NotificationWithBooksAndReservation>>
    suspend fun addNotification(notification: Notification)
}