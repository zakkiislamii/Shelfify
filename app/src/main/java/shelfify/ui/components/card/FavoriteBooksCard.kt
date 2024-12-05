package shelfify.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shelfify.R
import shelfify.contracts.card.CardFavBooks
import shelfify.data.dataMapping.FavBooks
import shelfify.ui.theme.MainColor

class FavoriteBooksCard : CardFavBooks<FavBooks> {
    @Composable
    override fun CreateCard(
        item: FavBooks, onClick: () -> Unit,
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .border(
                    0.9.dp,
                    Color.DarkGray,
                    RoundedCornerShape(8.dp)
                )
                .fillMaxSize()
                .clickable { onClick() }
                .clip(RoundedCornerShape(8.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = item.title,
                    modifier = Modifier,
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = item.writer,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = item.category,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Total reservations: ${item.totalReservations}",
                        fontSize = 8.sp,
                        style = MaterialTheme.typography.labelSmall,
                        color = MainColor
                    )
                }

            }
        }
    }
}