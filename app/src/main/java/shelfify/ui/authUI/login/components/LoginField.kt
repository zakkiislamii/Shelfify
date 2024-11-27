package shelfify.ui.authUI.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import shelfify.contracts.field.Field
import shelfify.utils.helpers.PasswordHelper

class LoginField : Field {
    @Composable
    override fun CreateField(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        visualTransformation: VisualTransformation,
        isPasswordField: Boolean,
    ) {
        var isPasswordVisible by remember { mutableStateOf(false) }
        val pwHelper = PasswordHelper()

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label, color = Color.DarkGray) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.DarkGray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            visualTransformation = pwHelper.determineVisualTransformation(
                isPasswordField,
                isPasswordVisible
            ),
            trailingIcon = if (isPasswordField) {
                {
                    pwHelper.PasswordVisibilityToggle(isPasswordVisible) {
                        isPasswordVisible = !isPasswordVisible
                    }
                }
            } else null,
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
    ) = CreateField(
        value = value,
        onValueChange = onValueChange,
        label = "Email",
        visualTransformation = VisualTransformation.None,
        isPasswordField = false
    )

    @Composable
    fun PasswordField(
        value: String,
        onValueChange: (String) -> Unit,
    ) = CreateField(
        value = value,
        onValueChange = onValueChange,
        label = "Password",
        visualTransformation = PasswordVisualTransformation(),
        isPasswordField = true
    )
}


