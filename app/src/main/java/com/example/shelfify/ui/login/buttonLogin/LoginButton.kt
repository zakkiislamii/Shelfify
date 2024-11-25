package com.example.shelfify.ui.login.buttonLogin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.shelfify.components.button.Button
import com.example.shelfify.ui.theme.MainColor

class LoginButton : Button {
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
                containerColor = backgroundColor,
                contentColor = textColor
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 20.dp, 30.dp, 0.dp),
            border = BorderStroke(1.dp, borderColor)
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
            text = "Sign In",
            backgroundColor = MainColor,
            textColor = Color.White,
            textStyle = MaterialTheme.typography.titleMedium,
            fontSize = 14,
            borderColor = Color.Transparent,
            onClick = onClick
        )
    }
}

@Preview
@Composable
fun ButtonPreviews() {
    LoginButton().Button {}
}