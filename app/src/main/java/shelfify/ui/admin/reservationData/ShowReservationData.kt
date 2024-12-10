package shelfify.ui.admin.reservationData

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import shelfify.be.services.viewModel.AdminViewModel
import shelfify.be.services.viewModel.ReservationViewModel
import shelfify.ui.components.card.ReserveDataCard

class ShowReservationData {
    @Composable
    fun ReservationData(
        navController: NavController,
        adminViewModel: AdminViewModel,
        reservationViewModel: ReservationViewModel,
    ) {
        val reserveData by adminViewModel.memberReservations.collectAsState()
        LaunchedEffect(Unit) {
            adminViewModel.getMemberReservations()
        }
        Scaffold(
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(paddingValues),
                    contentAlignment = Alignment.TopCenter
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 0.dp),
                        contentPadding = PaddingValues(bottom = 16.dp),
                    ) {
                        items(items = reserveData) { reserveData ->
                            ReserveDataCard().CreateCard(
                                item = reserveData,
                                navController = navController,
                                reservationViewModel = reservationViewModel
                            )
                        }
                    }
                }
            }
        )
    }
}