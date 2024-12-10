package shelfify.be.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import shelfify.be.domain.models.Reservation
import shelfify.contracts.enumerations.Status
import shelfify.data.dataMapping.FavBooks
import shelfify.data.dataMapping.MemberReserveCardUI

@Dao
interface ReservationDao {

    @Query("UPDATE Books SET stock = stock - 1 WHERE book_id = :bookId")
    suspend fun decreaseBookStock(bookId: Int)

    @Transaction
    suspend fun insertReserveAndUpdateStock(bookId: Int, reservations: Reservation): Long {
        decreaseBookStock(bookId)
        return insertReservation(reservations)
    }

    @Query("SELECT * FROM Reservations WHERE user_id = :userId")
    fun getReservationsByUserId(userId: Int): Flow<List<Reservation>>

    @Query(
        """
    SELECT 
        b.book_id AS bookId,
        b.title AS title,
        b.writer AS writer,
        b.stock AS stock,
        b.category AS category,
        b.book_image AS bookImage,
        COUNT(r.book_id) AS totalReservations
    FROM Books b
    INNER JOIN Reservations r ON b.book_id = r.book_id
    GROUP BY b.book_id, b.title, b.writer, b.category, b.book_image
    ORDER BY totalReservations DESC
"""
    )
    fun getFavBooks(): Flow<List<FavBooks>>

    @Query(
        """
SELECT 
    u.user_id AS userId,
    u.full_name AS fullName,
    GROUP_CONCAT(b.book_id) AS bookId,
    GROUP_CONCAT(r.reservation_id) AS reservationId,
    GROUP_CONCAT(b.writer) AS writer,
    GROUP_CONCAT(b.book_image) AS bookImage,
    GROUP_CONCAT(b.title) AS title,
    r.status AS reservationStatus,
    COUNT(r.reservation_id) AS totalReserve,
    r.created_at AS createdAt,
    GROUP_CONCAT(b.title) AS bookTitles
FROM Users u
INNER JOIN Reservations r ON u.user_id = r.user_id
INNER JOIN Books b ON r.book_id = b.book_id
GROUP BY u.user_id, u.full_name, r.status
ORDER BY r.created_at DESC
"""
    )
    fun getMemberReservations(): Flow<List<MemberReserveCardUI>>

    @Query(
        """
    UPDATE Reservations 
    SET status = :newStatus 
    WHERE user_id = :userId AND reservation_id = :reservationId
"""
    )
    suspend fun updateReservationStatus(userId: Int, reservationId: Int, newStatus: Status)

    @Insert
    suspend fun insertReservation(reservation: Reservation): Long
}
