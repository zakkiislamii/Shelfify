package com.example.shelfify.ui.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.shelfify.components.field.Field


class LoginField : Field {
    @Composable
    override fun CreateField(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        visualTransformation: VisualTransformation,
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label, color = Color.DarkGray) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.DarkGray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            visualTransformation = visualTransformation,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp, 20.dp, 0.dp)
        )
    }

    @Composable
    fun EmailField(
        value: String,
        onValueChange: (String) -> Unit,
    ) {
        CreateField(
            value = value,
            onValueChange = onValueChange,
            label = "Email",
            visualTransformation = VisualTransformation.None
        )
    }

    @Composable
    fun PasswordField(
        value: String,
        onValueChange: (String) -> Unit,

        ) {
        CreateField(
            value = value,
            onValueChange = onValueChange,
            label = "Password",
            visualTransformation = PasswordVisualTransformation()
        )
    }
}
