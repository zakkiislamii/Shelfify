package shelfify.ui.onBoard.components.boardButton

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ButtonPreviews() {
    Column {
        NextButton().Button() {}
        BackButton().Button() {}
    }
}