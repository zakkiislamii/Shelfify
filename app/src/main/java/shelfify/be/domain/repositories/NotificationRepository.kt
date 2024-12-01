package shelfify.be.domain.repositories

import kotlinx.coroutines.flow.Flow
import shelfify.be.dao.NotificationDao
import shelfify.be.domain.models.Notification
import shelfify.be.domain.repositoryContract.NotificationRepositoryContract
import shelfify.data.NotificationWithBooksAndReservation

class NotificationRepository(private val notificationDao: NotificationDao) :
    NotificationRepositoryContract {

    override fun getNotificationsByUserId(userId: Int): Flow<List<NotificationWithBooksAndReservation>> {
        return notificationDao.getNotificationsByUserId(userId)
    }

    override suspend fun addNotification(notification: Notification) {
        notificationDao.addNotification(notification)
    }
}
