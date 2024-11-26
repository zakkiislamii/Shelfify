package com.example.shelfify.ui.authUI.register.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun ToLogin(onClick: () -> Unit = {}) {
    Text(
        text = buildAnnotatedString {
            append("Have an account? ")
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append("Sign In.")
            pop()
        },
        color = Color(0xFF525252),
        fontSize = 12.sp,
        modifier = androidx.compose.ui.Modifier.clickable { onClick() }
    )
}

@Preview
@Composable
fun ToLoginPreview() {
    ToLogin()
}
