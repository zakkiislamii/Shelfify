package shelfify.be.domain.repositories

import kotlinx.coroutines.flow.Flow
import shelfify.be.dao.CartDao
import shelfify.be.domain.models.CartEntity
import shelfify.be.domain.repositoryContract.CartRepositoryContract
import shelfify.data.dataMapping.CartWithBook

class CartRepository(private val cartDao: CartDao) : CartRepositoryContract {

    override suspend fun addCart(cart: CartEntity) {
        return cartDao.insertCartAndUpdateStock(cart)
    }

    override suspend fun deleteCartByUserId(userId: Int) {
        return cartDao.deleteCartByUserId(userId)
    }

    override suspend fun deleteCart(cartId: Int, bookId: Int) {
        return cartDao.deleteCartAndUpdateStock(cartId, bookId)
    }

    override suspend fun deleteCartAfterReserve(bookId: Int) {
        return cartDao.deleteCartAfterReserve(bookId)
    }

    override suspend fun isBookExistsInCart(userId: Int, bookId: Int): Boolean {
        return cartDao.isBookExistsInCart(userId, bookId)
    }

    override suspend fun getCartsWithBooksByUserId(userId: Int): Flow<List<CartWithBook>> {
        return cartDao.getCartsWithBooksByUserId(userId)
    }

}
