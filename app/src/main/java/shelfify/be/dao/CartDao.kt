package shelfify.be.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import shelfify.be.domain.models.CartEntity
import shelfify.data.dataMapping.CartWithBook

@Dao
interface CartDao {

    @Query("UPDATE Books SET stock = stock - 1 WHERE book_id = :bookId")
    suspend fun decreaseBookStock(bookId: Int)

    @Query("UPDATE Books SET stock = stock + 1 WHERE book_id = :bookId")
    suspend fun increaseBookStock(bookId: Int)

    @Transaction
    suspend fun insertCartAndUpdateStock(cart: CartEntity) {
        decreaseBookStock(cart.bookId)
        insertCart(cart)
    }

    @Transaction
    suspend fun deleteCartAndUpdateStock(cartId: Int, bookId: Int) {
        deleteCartById(cartId)
        increaseBookStock(bookId)
    }

    @Insert
    suspend fun insertCart(cart: CartEntity)

    @Query("DELETE FROM Carts WHERE cart_id = :cartId")
    suspend fun deleteCartById(cartId: Int)

    @Query("DELETE FROM Carts WHERE user_id = :userId")
    suspend fun deleteCartByUserId(userId: Int)

    @Query("DELETE FROM Carts WHERE book_id = :bookId")
    suspend fun deleteCartAfterReserve(bookId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM Carts WHERE user_id = :userId AND book_id = :bookId)")
    suspend fun isBookExistsInCart(userId: Int, bookId: Int): Boolean

    @Query(
        """
        SELECT 
            c.cart_id AS cartId,
            c.user_id AS userId,
            c.book_id AS bookId,
            b.title AS bookTitle,
            b.writer AS bookWriter,
            b.book_image AS bookImage,
            b.category AS bookCategory
        FROM Carts c
        INNER JOIN Books b ON c.book_id = b.book_id
        WHERE c.user_id = :userId
    """
    )
    fun getCartsWithBooksByUserId(userId: Int): Flow<List<CartWithBook>>

}