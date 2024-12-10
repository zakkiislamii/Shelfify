package shelfify.ui.admin.historyMember

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
import shelfify.ui.admin.historyMember.components.HistoryMemberHeader
import shelfify.ui.components.card.MemberHistoryCard

class ShowHistoryMemberScreen {
    @Composable
    fun HistoryMember(
        navController: NavController,
        adminViewModel: AdminViewModel,
        userId: Int,
        fullName: String,
    ) {
        val memberHistory by adminViewModel.memberHistoryForAdmin.collectAsState()
        LaunchedEffect(userId) {
            adminViewModel.getMemberHistoryForAdmin(userId)
        }
        Scaffold(
            topBar = {
                HistoryMemberHeader(fullName = fullName) {
                    navController.popBackStack()
                }
            },
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
                            .padding(top = 40.dp, start = 10.dp, end = 10.dp, bottom = 0.dp),
                        contentPadding = PaddingValues(bottom = 16.dp),
                    ) {
                        items(memberHistory) { historyItem ->
                            MemberHistoryCard().CreateCard(item = historyItem)
                        }
                    }
                }
            }
        )
    }

}