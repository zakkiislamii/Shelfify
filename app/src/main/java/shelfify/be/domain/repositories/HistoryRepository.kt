package shelfify.be.domain.repositories

import kotlinx.coroutines.flow.Flow
import shelfify.be.dao.HistoryDao
import shelfify.be.domain.models.History
import shelfify.be.domain.repositoryContract.HistoryRepositoryContract
import shelfify.contracts.enumerations.Status
import shelfify.data.dataMapping.HistoryWithBooksAndReservation
import shelfify.data.dataMapping.MemberHistoryCardUI

class HistoryRepository(private val historyDao: HistoryDao) : HistoryRepositoryContract {
    override fun getHistoryByUserIdAndStatus(
        userId: Int,
        status: Status,
    ): Flow<List<HistoryWithBooksAndReservation>> {
        return historyDao.getHistoryByUserIdAndStatus(userId, status)
    }

    override fun getMemberHistoryByUserIdForAdmin(
        userId: Int,
    ): Flow<List<MemberHistoryCardUI>> {
        return historyDao.getMemberHistoryByUserIdForAdmin(userId)
    }

    override fun getMemberHistoryByUserId(
        userId: Int,
    ): Flow<List<MemberHistoryCardUI>> {
        return historyDao.getMemberHistoryByUserId(userId)
    }

    override suspend fun addHistory(history: History) {
        return historyDao.addHistory(history)
    }
}