package com.example.shelfify.contracts.dropDown

import androidx.compose.runtime.Composable

interface DropdownField {
    @Composable
    fun CreateDropdownField(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        options: List<String>
    )
}