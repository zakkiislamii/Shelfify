package shelfify.ui.auth.register.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import shelfify.contracts.dropDown.DropdownField

class FacultyDropdown : DropdownField {
    private fun formatText(text: String): String {
        return text.split(" ").joinToString(" ") { word ->
            word.lowercase().replaceFirstChar { it.uppercase() }
        }
    }

    private fun filterOptions(query: String, options: List<String>): List<String> {
        if (query.isBlank()) return options
        return options.filter {
            it.lowercase().contains(query.lowercase())
        }
    }

    @Composable
    override fun CreateDropdownField(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        options: List<String>,
    ) {
        var expanded by remember { mutableStateOf(false) }
        var textFieldValue by remember { mutableStateOf(value) }
        var filteredOptions by remember { mutableStateOf(options) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp, 20.dp, 0.dp)
        ) {
            OutlinedTextField(
                value = textFieldValue,
                onValueChange = { newValue ->
                    textFieldValue = newValue
                    filteredOptions = filterOptions(newValue, options)
                    expanded = true
                    onValueChange(newValue)
                },
                label = { Text(label, color = Color.DarkGray) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.DarkGray,
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = "Dropdown Arrow",
                        tint = Color.DarkGray,
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                filteredOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            val formattedText = formatText(option)
                            textFieldValue = formattedText
                            onValueChange(formattedText)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}