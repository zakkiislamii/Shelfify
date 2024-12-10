package shelfify.ui.library.bookDetail.components.buttonBookDetail


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import shelfify.contracts.button.Button
import shelfify.ui.theme.MainColor

class ButtonConfirmationBookDetail : Button {
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
            border = BorderStroke(1.dp, borderColor)
        ) {
            Text(
                text = text, fontSize = fontSize.sp, style = textStyle
            )
        }
    }

    @Composable
    fun YesButton(onClick: () -> Unit) {
        CreateButton(
            text = "Yes",
            backgroundColor = MainColor,
            textColor = Color.White,
            textStyle = MaterialTheme.typography.titleMedium,
            fontSize = 14,
            borderColor = Color.Transparent,
            onClick = onClick
        )
    }

    @Composable
    fun CancelButton(onClick: () -> Unit) {
        CreateButton(
            text = "No",
            backgroundColor = Color.White,
            textColor = Color.Black,
            textStyle = MaterialTheme.typography.titleMedium,
            fontSize = 14,
            borderColor = MainColor,
            onClick = onClick
        )
    }

    @Composable
    fun OKButton(onClick: () -> Unit) {
        CreateButton(
            text = "OK",
            backgroundColor = MainColor,
            textColor = Color.White,
            textStyle = MaterialTheme.typography.titleMedium,
            fontSize = 14,
            borderColor = Color.Transparent,
            onClick = onClick
        )
    }
}
