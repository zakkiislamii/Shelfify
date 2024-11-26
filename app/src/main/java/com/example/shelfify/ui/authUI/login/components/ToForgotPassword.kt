package com.example.shelfify.ui.authUI.login.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun ToForgotPassword() {
    Text(text = "Forgot password", color = Color(0xFF525252), modifier = Modifier, fontSize = 11.sp)
}