package shelfify.ui.components.card

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import shelfify.contracts.card.CardMemberHistoryComponent
import shelfify.data.dataMapping.MemberHistoryCardUI
import java.text.SimpleDateFormat
import java.util.Locale

class MemberHistoryCard : CardMemberHistoryComponent<MemberHistoryCardUI> {
    @Composable
    override fun CreateCard(
        item: MemberHistoryCardUI,
    ) {
        // Format the createdAt date
        val dateFormat = SimpleDateFormat("dd/MM/yyyy\nHH:mm", Locale.getDefault())
        val formattedTime = item.createdAt.let { dateFormat.format(it) } ?: "-"

        Card(
            modifier = Modifier
                .padding(4.dp)
                .border(
                    0.9.dp,
                    Color.DarkGray,
                    RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    Column(
                        modifier = Modifier
                            .padding(15.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = item.reservationStatus.name,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "Total Reservation: ${item.totalReserve}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = "Book Titles:",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        item.bookTitles.split(",").forEachIndexed { _, title ->
                            Text(
                                text = "- ${title.trim()}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 4,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(bottom = 8.dp, start = 10.dp)
                            )
                        }
                    }
                }
                Text(
                    text = formattedTime,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                )
            }
        }
    }
}
