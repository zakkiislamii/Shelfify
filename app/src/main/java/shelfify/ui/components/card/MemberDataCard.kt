package shelfify.ui.components.card

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.shelfify.R
import shelfify.contracts.card.CardMemberComponent
import shelfify.data.dataMapping.MemberDataCardUI

class MemberDataCard : CardMemberComponent<MemberDataCardUI> {
    @Composable
    override fun CreateCard(
        item: MemberDataCardUI,
        onHistoryClick: () -> Unit,
        onDeleteClick: () -> Unit,
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .border(
                    0.9.dp,
                    Color.DarkGray,
                    RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    Column(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = item.fullName,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = item.email,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = item.faculty,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = item.phoneNumber,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }

                //icon history
                IconButton(
                    onClick = { onHistoryClick() },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.history),
                        contentDescription = "History",
                        tint = Color.Black
                    )
                }

                // Icon Delete
                IconButton(
                    onClick = { onDeleteClick() },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "delete",
                        tint = Color.Black
                    )
                }

            }
        }
    }
}
