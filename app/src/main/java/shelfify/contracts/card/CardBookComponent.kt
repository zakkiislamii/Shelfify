package shelfify.contracts.card

import androidx.compose.runtime.Composable
import shelfify.data.baseUI.BookDataBaseUI

interface CardBookComponent<T : BookDataBaseUI> {
    @Composable
    fun CreateCard(item: T, onClick: () -> Unit)
}