package shelfify.be.domain.repositoryContract

import kotlinx.coroutines.flow.Flow
import shelfify.be.domain.models.History
import shelfify.contracts.enumerations.Status
import shelfify.data.HistoryWithBooksAndReservation

interface HistoryRepositoryContract {
    fun getHistoryByUserIdAndStatus(
        userId: Int,
        status: Status,
    ): Flow<List<HistoryWithBooksAndReservation>>

    suspend fun addHistory(history: History)

}