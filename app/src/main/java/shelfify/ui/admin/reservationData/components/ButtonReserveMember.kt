package shelfify.ui.admin.reservationData.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import shelfify.contracts.button.Button
import shelfify.ui.theme.MainColor

class ButtonReserveMember : Button {
    @Composable
    override fun CreateButton(
        text: String,
        backgroundColor: Color,
        textColor: Color,
        borderColor: Color,
        textStyle: TextStyle,
        fontSize: Int,
        onClick: () -> Unit,
    ) {
        androidx.compose.material3.Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor, contentColor = textColor
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(0.dp, 10.dp, 10.dp, 10.dp),
            border = BorderStroke(1.dp, borderColor)
        ) {
            Text(
                text = text, fontSize = fontSize.sp, style = textStyle, textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    fun ViewReservationButton(onClick: () -> Unit) {
        CreateButton(
            text = "View\nDetails",
            backgroundColor = MainColor,
            textColor = Color.White,
            textStyle = MaterialTheme.typography.titleMedium,
            fontSize = 14,
            borderColor = Color.Transparent,
            onClick = onClick
        )
    }

    @Composable
    fun RejectedButton(onClick: () -> Unit) {
        CreateButton(
            text = "Reject",
            backgroundColor = Color.Red,
            textColor = Color.White,
            textStyle = MaterialTheme.typography.titleMedium,
            fontSize = 14,
            borderColor = Color.Transparent,
            onClick = onClick
        )
    }

    @Composable
    fun AcceptReservationButton(onClick: () -> Unit) {
        CreateButton(
            text = "Accept",
            backgroundColor = MainColor,
            textColor = Color.White,
            textStyle = MaterialTheme.typography.titleMedium,
            fontSize = 14,
            borderColor = Color.Transparent,
            onClick = onClick
        )
    }

    @Composable
    fun ReturnedButton(onClick: () -> Unit) {
        CreateButton(
            text = "Returned",
            backgroundColor = Color(0xffCDE8E5),
            textColor = Color.Black,
            textStyle = MaterialTheme.typography.titleMedium,
            fontSize = 14,
            borderColor = Color.Transparent,
            onClick = onClick
        )
    }

}

