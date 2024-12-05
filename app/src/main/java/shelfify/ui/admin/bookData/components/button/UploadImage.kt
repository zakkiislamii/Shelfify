package shelfify.ui.admin.bookData.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shelfify.R
import shelfify.contracts.button.Button

class UploadImage : Button {
    @Composable
    override fun CreateButton(
        text: String,
        backgroundColor: Color,
        textColor: Color,
        borderColor: Color,
        textStyle: TextStyle,
        fontSize: Int,
        onClick: () -> Unit,
    ) {
        androidx.compose.material3.Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = textColor
            ),
            shape = RoundedCornerShape(13.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 10.dp, 0.dp, 10.dp)
                .border(
                    BorderStroke(1.dp, borderColor),
                    RoundedCornerShape(10.dp)
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween, // To space out text and image
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, bottom = 6.dp)
            ) {
                Text(
                    text = text,
                    fontSize = fontSize.sp,
                    style = textStyle,
                    color = Color.DarkGray,
                    modifier = Modifier.weight(1f) // This will make the Text push to the left
                )
                Image(
                    painter = painterResource(id = R.drawable.upload),
                    contentDescription = "upload",
                )
            }

        }
    }

    @Composable
    fun Button(onClick: () -> Unit) {
        CreateButton(
            text = "Book Image",
            backgroundColor = Color.White,
            textColor = Color.Black,
            textStyle = MaterialTheme.typography.titleMedium,
            fontSize = 14,
            borderColor = Color.Black,
            onClick = onClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ButtonImagePreviews() {
    UploadImage().Button {}
}
