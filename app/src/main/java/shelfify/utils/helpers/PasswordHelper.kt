package shelfify.utils.helpers

import androidx.compose.foundation.Image
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.shelfify.R

class PasswordHelper {
    fun determineVisualTransformation(
        isPasswordField: Boolean,
        isPasswordVisible: Boolean,
    ): VisualTransformation =
        if (isPasswordField && !isPasswordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }


    @Composable
    fun PasswordVisibilityToggle(
        isPasswordVisible: Boolean,
        onToggle: () -> Unit,
    ) = IconButton(onClick = onToggle) {
        Image(
            painter = painterResource(
                id = if (isPasswordVisible) R.drawable.visible else R.drawable.unvisible
            ),
            contentDescription = if (isPasswordVisible) {
                "Hide password"
            } else {
                "Show password"
            }
        )
    }
}
