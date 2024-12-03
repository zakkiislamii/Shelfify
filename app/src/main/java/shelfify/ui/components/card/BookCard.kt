package shelfify.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shelfify.R
import shelfify.contracts.card.CardBookComponent
import shelfify.data.dataMapping.BookUI
import shelfify.ui.theme.MainColor

class BookCard : CardBookComponent<BookUI> {
    @Composable
    override fun CreateCard(item: BookUI, onClick: () -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = item.bookImage.toInt()),
                    contentDescription = item.title,
                    modifier = Modifier
                        .width(120.dp)
                        .fillMaxSize()
                        .fillMaxHeight(),
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
                        text = "Book stock: ${item.stock}",
                        fontSize = 9.sp,
                        style = MaterialTheme.typography.labelSmall,
                        color = MainColor
                    )
                }
            }
        }
    }

    @Composable
    fun BookCardPreview() {
        val sampleBook = BookUI(
            bookImage = R.drawable.ic_launcher_background,
            title = "Sample Book Title",
            writer = "Author Name",
            category = "Category Name",
            stock = 10,
            bookId = 10
        )

        BookCard().CreateCard(
            item = sampleBook,
            onClick = { /* handle click */ }
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun BookCardWithBackgroundPreview() {
        // Preview the BookCard with background color
        Card(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            BookCardPreview()
        }
    }
}
