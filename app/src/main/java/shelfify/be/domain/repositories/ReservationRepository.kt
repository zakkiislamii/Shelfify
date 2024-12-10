package shelfify.be.domain.repositories

import kotlinx.coroutines.flow.Flow
import shelfify.be.dao.ReservationDao
import shelfify.be.domain.models.Reservation
import shelfify.be.domain.repositoryContract.ReservationRepositoryContract
import shelfify.contracts.enumerations.Status
import shelfify.data.dataMapping.FavBooks
import shelfify.data.dataMapping.MemberReserveCardUI

class ReservationRepository(private val reservationDao: ReservationDao) :
    ReservationRepositoryContract {
    override fun getReservationsByUserId(userId: Int): Flow<List<Reservation>> {
        return reservationDao.getReservationsByUserId(userId)
    }

    override suspend fun insertReservation(reservation: Reservation): Long {
        return reservationDao.insertReservation(reservation)
    }

    override suspend fun addReservationSingle(bookId: Int, reservations: Reservation): Long {
        return reservationDao.insertReserveAndUpdateStock(bookId, reservations)
    }

    override fun getFavBooks(): Flow<List<FavBooks>> {
        return reservationDao.getFavBooks()
    }

    override fun getMemberReservations(): Flow<List<MemberReserveCardUI>> {
        return reservationDao.getMemberReservations()
    }

    override suspend fun updateReservationStatus(
        userId: Int,
        reservationId: Int,
        newStatus: Status,
    ) {
        return reservationDao.updateReservationStatus(userId, reservationId, newStatus)
    }

    override suspend fun deleteReservationsByUserId(userId: Int) {
        return reservationDao.deleteReservationsByUserId(userId)
    }

}