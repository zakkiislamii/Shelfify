package shelfify.be.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import shelfify.be.domain.models.Reservation
import shelfify.data.dataMapping.FavBooks

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

    @Insert
    suspend fun insertReservation(reservation: Reservation): Long
}
