package shelfify.contracts.card

import androidx.compose.runtime.Composable

interface CardViewReserveComponent {
    @Composable
    fun CreateCard(
        bookImage: String?,
        title: String,
        writer: String,
    )
}