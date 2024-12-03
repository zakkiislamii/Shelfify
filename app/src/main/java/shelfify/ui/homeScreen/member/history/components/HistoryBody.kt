package shelfify.ui.homeScreen.member.history.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.shelfify.R
import shelfify.data.dataMapping.HistoryBookUI
import shelfify.data.dataMapping.HistoryWithBooksAndReservation
import shelfify.ui.components.card.HistoryCard

@Composable
fun HistoryBody(historyList: List<HistoryWithBooksAndReservation>) {
    Column {
        historyList.forEach { item ->
            val imageResource = if (item.bookImage?.startsWith("http") == true) {
                R.drawable.ic_launcher_background
            } else {
                item.bookImage?.toIntOrNull()
                    ?: R.drawable.ic_launcher_background
            }

            HistoryCard().CreateCard(
                item = HistoryBookUI(
                    bookImage = imageResource,
                    title = item.bookTitles,
                    writer = item.bookWriter,
                    bookId = item.bookId
                ),
                onClick = { /* handle click */ }
            )
        }
    }
}
