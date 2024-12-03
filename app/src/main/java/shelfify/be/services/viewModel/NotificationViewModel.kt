package shelfify.be.services.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import shelfify.be.domain.repositories.NotificationRepository
import shelfify.data.dataMapping.NotificationWithBooksAndReservation

class NotificationViewModel(private val notificationRepository: NotificationRepository) :
    ViewModel() {
    fun getNotificationsByUserId(userId: Int): Flow<List<NotificationWithBooksAndReservation>> {
        return notificationRepository.getNotificationsByUserId(userId)
    }
}
