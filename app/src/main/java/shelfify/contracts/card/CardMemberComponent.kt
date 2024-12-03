package shelfify.contracts.card

import androidx.compose.runtime.Composable
import shelfify.data.baseUI.MemberDataBaseUI

interface CardMemberComponent<T : MemberDataBaseUI> {
    @Composable
    fun CreateCard(
        item: T, onHistoryClick: () -> Unit,
        onDeleteClick: () -> Unit,
    )
}