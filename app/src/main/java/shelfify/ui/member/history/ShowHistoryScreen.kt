package shelfify.ui.member.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import shelfify.be.services.viewModel.HistoryViewModel
import shelfify.contracts.enumerations.Status
import shelfify.contracts.session.UserSessionData
import shelfify.data.dataMapping.HistoryBookUI
import shelfify.ui.components.card.HistoryCard
import shelfify.ui.member.history.components.HistoryHeader
import shelfify.utils.proxy.RealUserSessionData
import shelfify.utils.proxy.UserSessionProxy
import shelfify.utils.toast.CustomToast

class ShowHistoryScreen {
    @Composable
    fun HistoryScreen(navController: NavController, historyViewModel: HistoryViewModel) {
        var selectedTab by remember { mutableStateOf(Status.PENDING) }
        val context = LocalContext.current
        val userSessionData: UserSessionData = UserSessionProxy(RealUserSessionData())
        val userSession = userSessionData.getUserSession(context)
        val userId = userSession.userId.toInt()

        Scaffold(
            topBar = {
                HistoryHeader(selectedTab = selectedTab, onTabSelected = { tab ->
                    selectedTab = tab
                })
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    val historyList by historyViewModel.getHistoryByUserId(userId, selectedTab)
                        .collectAsState(initial = emptyList())
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(4.dp)
                    ) {
                        items(historyList) { item ->
                            HistoryCard().CreateCard(
                                item = HistoryBookUI(
                                    bookImage = item.bookImage ?: "",
                                    title = item.bookTitles,
                                    writer = item.bookWriter,
                                    bookId = item.bookId
                                ),
                                onClick = {
                                    if (item.stock > 0) {
                                        navController.navigate("bookDetail/${item.bookId}")
                                    } else {
                                        CustomToast().showToast(
                                            context,
                                            "Buku dengan judul ${item.bookTitles} telah habis!"
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}