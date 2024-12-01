package shelfify.be.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import shelfify.be.domain.models.Reservation

@Dao
interface ReservationDao {

    @Query("UPDATE Books SET stock = stock - 1 WHERE book_id = :bookId")
    suspend fun decreaseBookStock(bookId: Int)

    @Transaction
    suspend fun insertReserveAndUpdateStock(bookId: Int, reservations: Reservation): Long {
        decreaseBookStock(bookId)
        return addReservationsOnBookDetail(reservations)
    }

    @Query("SELECT * FROM Reservations WHERE user_id = :userId")
    fun getReservationsByUserId(userId: Int): Flow<List<Reservation>>

    @Insert
    suspend fun addReservations(reservations: List<Reservation>)

    @Insert
    suspend fun addReservationsOnBookDetail(reservations: Reservation): Long

    @Insert
    suspend fun insertReservation(reservation: Reservation): Long
}
