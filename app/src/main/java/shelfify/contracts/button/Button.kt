package shelfify.contracts.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

interface Button {
    @Composable
    fun CreateButton(
        text: String,
        backgroundColor: Color,
        textColor: Color,
        borderColor: Color,
        textStyle: TextStyle,
        fontSize: Int,
        onClick: () -> Unit
    )
}