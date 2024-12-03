package shelfify.ui.components.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import shelfify.data.modal.ModalConfig
import shelfify.ui.library.bookDetail.components.buttonBookDetail.ButtonConfirmationBookDetail
import shelfify.ui.theme.MainColor

@Composable
fun ConfirmationModal(
    isVisible: Boolean,
    config: ModalConfig,
    onDismiss: () -> Unit,
) {
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(Float.MAX_VALUE),
            contentAlignment = Alignment.Center
        ) {
            // Blur background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(10.dp)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(onClick = onDismiss)
            )

            // Modal content
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .width(280.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = config.title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MainColor
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = config.message,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ButtonConfirmationBookDetail().CancelButton {
                            config.onCancel()
                            onDismiss()
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        ButtonConfirmationBookDetail().YesButton {
                            config.onConfirm()
                            onDismiss()
                        }
                    }
                }
            }
        }
    }
}