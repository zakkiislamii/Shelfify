package shelfify.be.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import shelfify.be.domain.models.History
import shelfify.contracts.enumerations.Status
import shelfify.data.HistoryWithBooksAndReservation

@Dao
interface HistoryDao {
    @Insert
    suspend fun addHistory(history: History)

    @Transaction
    @Query(
        """
        SELECT r.status AS reservationStatus, 
               b.book_image AS bookImage, 
               b.book_id AS bookId,
               b.title AS bookTitles, 
               b.writer AS bookWriter, 
               h.created_at AS createdAt
        FROM Histories h
        INNER JOIN Reservations r ON h.reservation_id = r.reservation_id
        INNER JOIN Books b ON h.book_id = b.book_id
        WHERE h.user_id = :userId
        AND r.status = :status
        ORDER BY h.created_at DESC
    """
    )
    fun getHistoryByUserIdAndStatus(
        userId: Int,
        status: Status,
    ): Flow<List<HistoryWithBooksAndReservation>>
}
