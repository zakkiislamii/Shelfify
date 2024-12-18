package shelfify.ui.onBoard.components.boardButton

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import shelfify.contracts.button.Button
import shelfify.ui.theme.MainColor

class BackButton : Button {
    @Composable
    override fun CreateButton(
        text: String,
        backgroundColor: Color,
        textColor: Color,
        borderColor: Color,
        textStyle: TextStyle,
        fontSize: Int,
        onClick: () -> Unit
    ) {
        androidx.compose.material3.Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = textColor
            ),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, borderColor),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(
                text = text,
                fontSize = fontSize.sp,
                style = textStyle
            )
        }
    }

    @Composable
    fun Button(onClick: () -> Unit) {
        CreateButton(
            text = "Back",
            backgroundColor = Color.Transparent,
            textColor = Color.Black,
            borderColor = MainColor,
            textStyle = MaterialTheme.typography.bodySmall,
            fontSize = 13,
            onClick = onClick
        )
    }
}