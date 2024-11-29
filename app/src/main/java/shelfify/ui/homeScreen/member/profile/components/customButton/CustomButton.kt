package shelfify.ui.homeScreen.member.profile.components.customButton

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import shelfify.contracts.button.Button

class CustomButton : Button {
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
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor
            ),
            border = BorderStroke(1.dp, borderColor),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.height(40.dp)
        ) {
            Text(
                text = text,
                color = textColor,
                style = textStyle,
                fontSize = fontSize.sp
            )
        }
    }
}