package shelfify.ui.member.history.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import shelfify.data.dataMapping.HistoryBookUI
import shelfify.data.dataMapping.HistoryWithBooksAndReservation
import shelfify.ui.components.card.HistoryCard

@Composable
fun HistoryBody(historyList: List<HistoryWithBooksAndReservation>, navController: NavController) {
    Column {
        historyList.forEach { item ->
            HistoryCard().CreateCard(
                item = HistoryBookUI(
                    bookImage = item.bookImage ?: "",
                    title = item.bookTitles,
                    writer = item.bookWriter,
                    bookId = item.bookId
                ),
                onClick = { navController.navigate("bookDetail/${item.bookId}") }
            )
        }
    }
}
