package shelfify.be.domain.repositoryContract

import kotlinx.coroutines.flow.Flow
import shelfify.be.domain.models.Reservation
import shelfify.data.dataMapping.FavBooks

interface ReservationRepositoryContract {
    fun getReservationsByUserId(userId: Int): Flow<List<Reservation>>
    suspend fun insertReservation(reservation: Reservation): Long
    suspend fun addReservationSingle(bookId: Int, reservations: Reservation): Long
    fun getFavBooks(): Flow<List<FavBooks>>
}