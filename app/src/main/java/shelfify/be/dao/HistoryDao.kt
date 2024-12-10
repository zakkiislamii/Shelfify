package shelfify.be.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import shelfify.be.domain.models.History
import shelfify.contracts.enumerations.Status
import shelfify.data.dataMapping.HistoryWithBooksAndReservation
import shelfify.data.dataMapping.MemberHistoryCardUI

@Dao
interface HistoryDao {
    @Insert
    suspend fun addHistory(history: History)

    @Query("DELETE FROM Histories WHERE user_id = :userId")
    suspend fun deleteHistoryByUserId(userId: Int)

    @Transaction
    @Query(
        """
     SELECT 
            r.status AS reservationStatus,
            b.book_image AS bookImage,
            b.title AS bookTitles,
            b.stock AS stock,
            b.writer AS bookWriter,
            b.book_id AS bookId,
            h.created_at AS createdAt
        FROM Histories h
        INNER JOIN Books b ON h.book_id = b.book_id
        INNER JOIN Reservations r ON h.reservation_id = r.reservation_id
        WHERE r.status = :status 
        AND h.user_id = :userId
        GROUP BY b.book_id, r.reservation_id
        ORDER BY h.created_at DESC
    """
    )
    fun getHistoryByUserIdAndStatus(
        userId: Int,
        status: Status,
    ): Flow<List<HistoryWithBooksAndReservation>>

    @Transaction
    @Query(
        """
    SELECT h.user_id AS userId,
               h.status_history AS reservationStatus,
               COUNT(DISTINCT r.reservation_id) AS totalReserve,
               h.created_at AS createdAt,
               GROUP_CONCAT(DISTINCT b.title) AS bookTitles
        FROM Histories h
        LEFT JOIN Reservations r ON h.reservation_id = r.reservation_id
        LEFT JOIN Books b ON h.book_id = b.book_id
        WHERE h.user_id = :userId
        GROUP BY h.created_at
        ORDER BY h.created_at DESC
    """
    )
    fun getMemberHistoryByUserIdForAdmin(
        userId: Int,
    ): Flow<List<MemberHistoryCardUI>>

    @Transaction
    @Query(
        """
 SELECT h.user_id AS userId,
       r.status AS reservationStatus,
       COUNT(DISTINCT r.reservation_id) AS totalReserve,
       h.created_at AS createdAt,
       GROUP_CONCAT(DISTINCT b.title) AS bookTitles
FROM Histories h
LEFT JOIN Reservations r ON h.reservation_id = r.reservation_id
LEFT JOIN Books b ON h.book_id = b.book_id
WHERE h.user_id = :userId
GROUP BY h.created_at
ORDER BY h.created_at DESC
    """
    )
    fun getMemberHistoryByUserId(
        userId: Int,
    ): Flow<List<MemberHistoryCardUI>>
}
