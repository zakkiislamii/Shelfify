package com.example.shelfify.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class ShowLoginScreen {
    @Composable
    fun Login() {
        Column {
            Text(text = "Logn", style = MaterialTheme.typography.headlineLarge)
        }
    }
}