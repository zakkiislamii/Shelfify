package shelfify.be.services.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import shelfify.be.domain.models.Notification
import shelfify.be.domain.repositories.NotificationRepository
import shelfify.data.NotificationWithBooksAndReservation

class NotificationViewModel(private val notificationRepository: NotificationRepository) :
    ViewModel() {

    fun getNotificationsByUserId(userId: Int): Flow<List<NotificationWithBooksAndReservation>> {
        return notificationRepository.getNotificationsByUserId(userId)
    }


    fun addNotification(notification: Notification) {
        viewModelScope.launch {
            notificationRepository.addNotification(notification)
        }
    }
}
