package shelfify.contracts.card

import androidx.compose.runtime.Composable
import shelfify.data.baseUI.BookDataBaseUI

interface CardBookDataMemberComponent<T : BookDataBaseUI> {
    @Composable
    fun CreateCard(
        item: T, onUpdateClick: () -> Unit, openBook: () -> Unit,
        onDeleteClick: () -> Unit,
    )
}