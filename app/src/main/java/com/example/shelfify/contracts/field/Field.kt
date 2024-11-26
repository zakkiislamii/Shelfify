package com.example.shelfify.contracts.field

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.VisualTransformation

interface Field {
    @Composable
    fun CreateField(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        visualTransformation: VisualTransformation,
        isPasswordField: Boolean,
    )
}
