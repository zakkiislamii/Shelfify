package shelfify.be.services.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import shelfify.be.domain.repositories.HistoryRepository
import shelfify.contracts.enumerations.Status
import shelfify.data.dataMapping.HistoryWithBooksAndReservation

class HistoryViewModel(private val historyRepository: HistoryRepository) : ViewModel() {

    fun getHistoryByUserId(
        userId: Int,
        status: Status,
    ): Flow<List<HistoryWithBooksAndReservation>> {
        return historyRepository.getHistoryByUserIdAndStatus(userId, status)
    }


}
