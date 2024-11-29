package shelfify.ui.homeScreen.home.bookDetail.components.buttonBookDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import shelfify.data.BookDetailData
import shelfify.ui.theme.MainColor


@Composable
fun BookDetailBody(
    book: BookDetailData,
) {
    val scrollState = rememberScrollState()

    // Outer Column is vertically scrollable
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(scrollState)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally, // All content in column will be centered horizontally
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = book.bookImage),
                contentDescription = "Book Cover",
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(1.dp, shape = RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = book.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = book.writer,
                fontSize = 11.sp,
                color = Color.DarkGray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // Row with horizontal scroll capability
        Row(
            modifier = Modifier
                .padding(20.dp)
                .shadow(1.dp, shape = RoundedCornerShape(1.dp))
                .fillMaxWidth()
                .horizontalScroll(scrollState), // Add horizontal scroll to the row
            horizontalArrangement = Arrangement.Center, // Center the content inside the row
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Columns inside the row with centered text
            Column(
                modifier = Modifier
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Language",
                    color = Color.Black,  fontSize = 8.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Center the text
                )
                Text(
                    text = book.language,
                    color = MainColor,  fontSize = 8.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Center the language
                )
            }
            Column(
                modifier = Modifier
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Stock",
                    color = Color.Black,  fontSize = 8.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Center the text
                )
                Text(
                    text = "${book.stock}",
                    color = MainColor,  fontSize = 8.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Center the stock
                )
            }
            Column(
                modifier = Modifier
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Pages",  fontSize = 8.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Center the text
                )
                Text(
                    text = "${book.pages}",
                    fontSize = 8.sp,
                    color = MainColor,
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Center the pages
                )
            }
            Column(
                modifier = Modifier
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Borrowing\n Duration",
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally), // Center the text
                    fontSize = 8.sp
                )
                Text(
                    text = "7 Days",
                    color = MainColor,
                    modifier = Modifier.align(Alignment.CenterHorizontally), // Center the borrowing duration
                    fontSize = 10.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonBookDetails().ReservationButton {}
            ButtonBookDetails().CartButton {}
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
        ) {
            Text(
                text = "Books Description",
                color = MainColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)

            )
            Text(
                text = book.description ?: "No description available",
                color = Color.Black,
                fontSize = 10.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
