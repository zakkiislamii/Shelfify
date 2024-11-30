package shelfify.ui.library.categoryBook

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import shelfify.data.DataCategoryBook


@Composable
fun CategoryItem(category: DataCategoryBook, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .height(140.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .border(2.dp, color = Color.Black, shape = RoundedCornerShape(5.dp))
                .background(
                    color = Color(0xFFCDE8E5)
                )
        ) {
            Image(
                painter = painterResource(id = category.iconRes),
                contentDescription = category.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .align(Alignment.Center)
            )
        }

        Text(
            text = category.name,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            fontSize = 11.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(horizontal = 6.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

