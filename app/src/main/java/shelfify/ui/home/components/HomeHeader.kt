package shelfify.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shelfify.R
import shelfify.ui.components.SearchBar
import shelfify.ui.theme.MainColor

@Preview(showBackground = true)
@Composable
fun HomeHeaderPreview() {
    HomeHeader(fullname = "Zidan")
}

@Composable
fun HomeHeader(fullname: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
    ) {
        // Background with clip
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(MainColor)
        )
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hi, $fullname",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Let's reservation a book today",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.cart),
                    contentDescription = "cart",
                    modifier = Modifier.size(50.dp)
                )
            }
        }

        // SearchBar placed on top of the clip, using Box with alignment
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd) // Align to the top
                .padding(10.dp, 10.dp, 10.dp, 15.dp)
        ) {
            SearchBar(
                onSearch = { query ->

                }
            )
        }
    }
}
