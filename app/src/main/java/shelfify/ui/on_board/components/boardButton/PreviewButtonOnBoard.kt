package shelfify.ui.on_board.components.boardButton

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import shelfify.ui.on_board.components.boardButton.BackButton
import shelfify.ui.on_board.components.boardButton.NextButton

@Preview
@Composable
fun ButtonPreviews() {
    Column {
        NextButton().Button() {}
        BackButton().Button() {}
    }
}