package shelfify.ui.library.bookDetail.components

import android.content.Context
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.shelfify.R
import shelfify.data.dataMapping.BookDetailData
import shelfify.data.modal.ModalConfig
import shelfify.data.modal.ModalConfigWarning
import shelfify.ui.components.modal.ConfirmationModal
import shelfify.ui.components.modal.WarningModal
import shelfify.ui.library.bookDetail.components.buttonBookDetail.ButtonBookDetails
import shelfify.ui.theme.MainColor

class BookDetailBody {
    @Composable
    fun BookDetail(
        book: BookDetailData,
        onAddToCart: () -> Unit,
        onReserve: () -> Unit,
        navController: NavController,
        hasActiveReservation: Boolean,
    ) {
        val context = LocalContext.current
        var showConfirmDialog by remember { mutableStateOf(false) }
        var showConfirmReservationDialog by remember { mutableStateOf(false) }
        var showWarningDialog by remember { mutableStateOf(false) }
        val scrollState = rememberScrollState()
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val role = sharedPreferences.getString("role", "") ?: ""

        if (book.stock == 0) {
            LaunchedEffect(Unit) {
                navController.navigate("home")
            }
            return
        }

        val cartModalConfig = remember {
            ModalConfig(
                title = "Cart Confirmation",
                message = "Are you sure you want to add this book to your cart?",
                onConfirm = onAddToCart,
                onCancel = {}
            )
        }

        val reservationModalConfig = remember {
            ModalConfig(
                title = "Reservation Confirmation",
                message = "Are you sure to reserve this book?",
                onConfirm = onReserve,
                onCancel = {}
            )
        }

        val warningModalConfig = remember {
            ModalConfigWarning(
                title = "Warning",
                message = "You cannot reserve new books until you return the borrowed books",
                onConfirm = { showWarningDialog = false }
            )
        }

        ConfirmationModal(
            isVisible = showConfirmDialog,
            config = cartModalConfig,
            onDismiss = { showConfirmDialog = false }
        )

        ConfirmationModal(
            isVisible = showConfirmReservationDialog,
            config = reservationModalConfig,
            onDismiss = { showConfirmReservationDialog = false }
        )

        WarningModal(
            isVisible = showWarningDialog,
            config = warningModalConfig,
            onDismiss = { showWarningDialog = false }
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
            if (role != "ADMIN") {
                BookActions(
                    onReservationClick = {
                        if (hasActiveReservation) {
                            showWarningDialog = true
                        } else {
                            showConfirmReservationDialog = true
                        }
                    },
                    onCartClick = {
                        if (hasActiveReservation) {
                            showWarningDialog = true
                        } else {
                            showConfirmDialog = true
                        }
                    }
                )
            }
            BookDescription(book)
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
    private fun BookImage(book: BookDetailData) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val imageUrl = book.bookImage ?: ""
            AsyncImage(
                model = if (imageUrl.isNotEmpty() && imageUrl.startsWith("https")) imageUrl else R.drawable.ic_launcher_background,
                contentDescription = book.title,
                modifier = Modifier
                    .fillMaxSize(),
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
                textAlign = TextAlign.Justify,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
