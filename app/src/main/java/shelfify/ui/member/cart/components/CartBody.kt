package shelfify.ui.member.cart.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import shelfify.be.services.viewModel.CartViewModel
import shelfify.data.dataMapping.CartBookUI
import shelfify.data.dataMapping.CartWithBook
import shelfify.ui.components.card.CartCard

@Composable
fun CartBody(
    cartList: List<CartWithBook>,
    isEmpty: Boolean,
    cartViewModel: CartViewModel,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (isEmpty) {
                Text(text = "Keranjang kosong")
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(cartList) { cartWithBook ->

                        val cardBookUI = CartBookUI(
                            bookId = cartWithBook.bookId,
                            bookImage = cartWithBook.bookImage ?: "",
                            title = cartWithBook.bookTitle,
                            writer = cartWithBook.bookWriter,
                            category = cartWithBook.bookCategory
                        )

                        CartCard().CreateCard(
                            item = cardBookUI,
                            onClick = {
                                cartViewModel.deleteCart(
                                    cartWithBook.cartId,
                                    cartWithBook.bookId,
                                    cartWithBook.userId
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}