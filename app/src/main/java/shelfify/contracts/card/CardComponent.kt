package shelfify.contracts.card

import androidx.compose.runtime.Composable
import shelfify.data.BaseUI

interface CardComponent<T : BaseUI> {
    @Composable
    fun CreateCard(item: T, onClick: () -> Unit)
}