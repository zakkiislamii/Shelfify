package shelfify.ui.member.profile.components.customField

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import shelfify.contracts.field.UserField

class CustomField : UserField {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun CreateField(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        visualTransformation: VisualTransformation,
        isPasswordField: Boolean,
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            visualTransformation = visualTransformation,
            modifier = Modifier.fillMaxWidth(),
            readOnly = isPasswordField
        )
    }
}