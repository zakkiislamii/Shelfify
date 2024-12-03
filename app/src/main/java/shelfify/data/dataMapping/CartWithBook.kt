package shelfify.data.dataMapping

data class CartWithBook(
    val cartId: Int,
    val userId: Int,
    val bookId: Int,
    val bookTitle: String,
    val bookWriter: String,
    val bookImage: String,
    val bookCategory: String,
)