package shelfify.contracts.card

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import shelfify.be.services.viewModel.ReservationViewModel
import shelfify.data.baseUI.MemberDataBaseUI

interface CardReserveComponent<T : MemberDataBaseUI> {
    @Composable
    fun CreateCard(
        item: T, navController: NavController, reservationViewModel: ReservationViewModel,
    )
}