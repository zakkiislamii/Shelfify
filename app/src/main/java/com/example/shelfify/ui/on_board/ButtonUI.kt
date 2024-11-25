package com.example.shelfify.ui.on_board

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import com.example.shelfify.components.button.Button
import com.example.shelfify.ui.theme.MainColor

object NextButton : Button {
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
        Button(
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
            text = "Next",
            backgroundColor = MainColor,
            textColor = Color.White,
            textStyle = MaterialTheme.typography.titleMedium,
            fontSize = 14,
            borderColor = Color.Transparent,
            onClick = onClick
        )
    }
}

object BackButton : Button {
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
        Button(
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

@Preview
@Composable
fun ButtonPreviews() {
    Column {
        NextButton.Button {}
        BackButton.Button {}
    }

}