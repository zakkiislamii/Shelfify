package shelfify.be.domain.repositoryContract

import kotlinx.coroutines.flow.Flow
import shelfify.be.domain.models.Reservation
import shelfify.contracts.enumerations.Status
import shelfify.data.dataMapping.FavBooks
import shelfify.data.dataMapping.MemberReserveCardUI

interface ReservationRepositoryContract {
    fun getReservationsByUserId(userId: Int): Flow<List<Reservation>>
    suspend fun insertReservation(reservation: Reservation): Long
    suspend fun addReservationSingle(bookId: Int, reservations: Reservation): Long
    fun getFavBooks(): Flow<List<FavBooks>>
    fun getMemberReservations(): Flow<List<MemberReserveCardUI>>
    suspend fun updateReservationStatus(userId: Int, reservationId: Int, newStatus: Status)
    suspend fun deleteReservationsByUserId(userId: Int)
}