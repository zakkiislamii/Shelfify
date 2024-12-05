package shelfify.contracts.card

import androidx.compose.runtime.Composable
import shelfify.data.baseUI.BookDataBaseUI

interface CardFavBooks<T : BookDataBaseUI> {
    @Composable
    fun CreateCard(
        item: T, onClick: () -> Unit,
    )
}