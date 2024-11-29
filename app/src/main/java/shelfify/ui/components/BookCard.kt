package shelfify.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import shelfify.data.BookUI
import shelfify.ui.theme.MainColor

@Composable
fun BookCard(
    book: BookUI,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),

        ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // Image on the left side
            Image(
                painter = painterResource(id = book.bookImage),
                contentDescription = book.title,
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxSize()
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )

            // Text content on the right side
            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxSize()
                    .weight(1f) // To take the remaining space
            ) {
                Text(
                    text = book.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp) // Space between title and writer
                )
                Text(
                    text = book.writer,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = book.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Book stock: ${book.stock}",
                    fontSize = 9.sp,
                    style = MaterialTheme.typography.labelSmall,
                    color = MainColor
                )
            }
        }
    }
}
