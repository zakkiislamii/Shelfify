package shelfify.contracts.field

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.VisualTransformation

interface UserField {
    @Composable
    fun CreateField(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        visualTransformation: VisualTransformation,
        isPasswordField: Boolean,
    )
}
