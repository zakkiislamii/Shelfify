package shelfify.be.domain.repositories

import shelfify.be.dao.CartDao
import shelfify.be.domain.models.CartEntity
import shelfify.data.CartWithBook

class CartRepository(private val cartDao: CartDao) {

    suspend fun addCart(cart: CartEntity) {
        return cartDao.insertCartAndUpdateStock(cart)
    }

    suspend fun deleteCart(cartId: Int, bookId: Int) {
        return cartDao.deleteCartAndUpdateStock(cartId, bookId)
    }

    suspend fun isBookExistsInCart(userId: Int, bookId: Int): Boolean {
        return cartDao.isBookExistsInCart(userId, bookId)
    }

    suspend fun getCartsWithBooksByUserId(userId: Int): List<CartWithBook> {
        return cartDao.getCartsWithBooksByUserId(userId)
    }
}
