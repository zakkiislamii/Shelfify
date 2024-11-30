package shelfify.ui.library.bookDetail.components.buttonBookDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import shelfify.data.BookDetailData
import shelfify.data.ModalConfig
import shelfify.ui.components.ConfirmationModal
import shelfify.ui.theme.MainColor

class BookDetailBody {
    @Composable
    fun BookDetail(
        book: BookDetailData,
        onAddToCart: () -> Unit,
    ) {
        var showConfirmDialog by remember { mutableStateOf(false) }
        val scrollState = rememberScrollState()

        // Modal configuration
        val cartModalConfig = remember {
            ModalConfig(
                title = "Cart Confirmation",
                message = "Are you sure you want to add this book to your cart?",
                onConfirm = onAddToCart,
                onCancel = {}
            )
        }

        // Confirmation Modal
        ConfirmationModal(
            isVisible = showConfirmDialog,
            config = cartModalConfig,
            onDismiss = { showConfirmDialog = false }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BookImage(book)
            BookStats(book, scrollState)
            BookActions(
                onReservationClick = {},
                onCartClick = { showConfirmDialog = true }
            )
            BookDescription(book)
        }
    }

    @Composable
    private fun BookImage(book: BookDetailData) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = book.bookImage),
                contentDescription = "Book Cover",
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(1.dp, shape = RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = book.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Text(
                text = book.writer,
                fontSize = 11.sp,
                color = Color.DarkGray,
            )
        }
    }

    @Composable
    private fun BookStats(book: BookDetailData, scrollState: ScrollState) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .shadow(1.dp, shape = RoundedCornerShape(1.dp))
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatItem("Language", book.language)
            StatItem("Stock", book.stock.toString())
            StatItem("Pages", book.pages.toString())
            StatItem("Borrowing\nDuration", "7 Days")
        }
    }

    @Composable
    private fun StatItem(label: String, value: String) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = label,
                color = Color.Black,
                fontSize = 8.sp,
            )
            Text(
                text = value,
                color = MainColor,
                fontSize = if (label.contains("\n")) 10.sp else 8.sp,
            )
        }
    }

    @Composable
    private fun BookActions(onReservationClick: () -> Unit, onCartClick: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonBookDetails().ReservationButton(onReservationClick)
            ButtonBookDetails().CartButton(onCartClick)
        }
    }

    @Composable
    private fun BookDescription(book: BookDetailData) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
        ) {
            Text(
                text = "Books Description",
                color = MainColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Text(
                text = book.description ?: "No description available",
                color = Color.Black,
                fontSize = 10.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}