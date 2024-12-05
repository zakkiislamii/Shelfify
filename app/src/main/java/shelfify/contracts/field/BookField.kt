package shelfify.contracts.field

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.VisualTransformation

interface BookField {
    @Composable
    fun CreateField(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        visualTransformation: VisualTransformation,
    )

    @Composable
    fun CreateImageField(
        value: String,
        onValueChange: (String) -> Unit,
        onUriSelected: (Uri) -> Unit,
        label: String,
    )
}