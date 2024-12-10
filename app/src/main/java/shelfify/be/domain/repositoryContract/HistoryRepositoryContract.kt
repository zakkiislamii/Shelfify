package shelfify.be.domain.repositoryContract

import kotlinx.coroutines.flow.Flow
import shelfify.be.domain.models.History
import shelfify.contracts.enumerations.Status
import shelfify.data.dataMapping.HistoryWithBooksAndReservation
import shelfify.data.dataMapping.MemberHistoryCardUI

interface HistoryRepositoryContract {
    fun getHistoryByUserIdAndStatus(
        userId: Int,
        status: Status,
    ): Flow<List<HistoryWithBooksAndReservation>>

    fun getMemberHistoryByUserId(
        userId: Int,
    ): Flow<List<MemberHistoryCardUI>>

    fun getMemberHistoryByUserIdForAdmin(
        userId: Int,
    ): Flow<List<MemberHistoryCardUI>>

    suspend fun addHistory(history: History)

}