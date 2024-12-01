package shelfify.be.domain.repositories

import kotlinx.coroutines.flow.Flow
import shelfify.be.dao.ReservationDao
import shelfify.be.domain.models.Reservation
import shelfify.be.domain.repositoryContract.ReservationRepositoryContract

class ReservationRepository(private val reservationDao: ReservationDao) :
    ReservationRepositoryContract {
    override fun getReservationsByUserId(userId: Int): Flow<List<Reservation>> {
        return reservationDao.getReservationsByUserId(userId)
    }

    override suspend fun insertReservation(reservation: Reservation): Long {
        return reservationDao.insertReservation(reservation)
    }

    override suspend fun addReservationSingle(bookId: Int, reservations: Reservation):Long {
        return reservationDao.insertReserveAndUpdateStock(bookId, reservations)
    }


}