package com.example.shelfify.ui.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun ToRegister() {
    Text(
        text = buildAnnotatedString {
            append("Don't have an account? ")
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append("Sign up.")
            pop()
        },
        color = Color(0xFF525252),
        fontSize = 12.sp
    )
}
