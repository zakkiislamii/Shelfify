package shelfify.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shelfify.R

data class Category(
    val id: Int,
    val name: String,
    val iconRes: Int = R.drawable.icon1,
)

object CategoryData {
    val categories = arrayOf(
        Category(1, "Action & Adventure", R.drawable.icon1),
        Category(2, "Antiques & Collectibles", R.drawable.icon2),
        Category(3, "Business & Economics", R.drawable.icon3),
        Category(4, "Computer", R.drawable.icon4),
        Category(5, "Design", R.drawable.icon5),
        Category(6, "Education", R.drawable.icon6),
        Category(7, "Engineering", R.drawable.icon7),
        Category(8, "Family & Relationship", R.drawable.icon8),
        Category(9, "Fiction", R.drawable.icon9),
        Category(10, "See All", R.drawable.allcategory),  // All Category icon
        Category(11, "Humor", R.drawable.icon10),
        Category(12, "Horror", R.drawable.icon11)
    )
}

@Composable
fun CategoryBook() {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
    ) {
        items(CategoryData.categories) { category ->
            CategoryItem(category = category)
        }
    }
}

@Composable
fun CategoryItem(category: Category) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .height(120.dp)
            .clickable { },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .border(2.dp, color = Color.Black, shape = RoundedCornerShape(5.dp))
                .background(color = Color(0xffCDE8E5))
        ) {
            // Image for the category
            Image(
                painter = painterResource(id = category.iconRes),
                contentDescription = category.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                    .align(Alignment.Center)
            )
        }

        // Display name under the image
        Text(
            text = category.name,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            fontSize = 11.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(horizontal = 6.dp)
                .align(Alignment.CenterHorizontally),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryBookPreview() {
    MaterialTheme {
        CategoryBook()
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    MaterialTheme {
        CategoryItem(
            category = Category(1, "Action & Adventure", R.drawable.icon1)
        )
    }
}
