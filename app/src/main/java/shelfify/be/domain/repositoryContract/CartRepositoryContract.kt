package shelfify.be.domain.repositoryContract

import kotlinx.coroutines.flow.Flow
import shelfify.be.domain.models.CartEntity
import shelfify.data.dataMapping.CartWithBook

interface CartRepositoryContract {
    suspend fun addCart(cart: CartEntity)
    suspend fun deleteCart(cartId: Int, bookId: Int)
    suspend fun isBookExistsInCart(userId: Int, bookId: Int): Boolean
    suspend fun getCartsWithBooksByUserId(userId: Int): Flow<List<CartWithBook>>
    suspend fun deleteCartAfterReserve(bookId: Int)
}