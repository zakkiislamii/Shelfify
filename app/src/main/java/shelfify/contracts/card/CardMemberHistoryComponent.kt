package shelfify.contracts.card

import androidx.compose.runtime.Composable
import shelfify.data.baseUI.MemberDataBaseUI

interface CardMemberHistoryComponent<T : MemberDataBaseUI> {
    @Composable
    fun CreateCard(
        item: T,
    )
}