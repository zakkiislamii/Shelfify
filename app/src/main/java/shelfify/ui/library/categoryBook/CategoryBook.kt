package shelfify.ui.library.categoryBook

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shelfify.R
import shelfify.data.categoryBook.CategoryData
import shelfify.data.categoryBook.DataCategoryBook
import shelfify.routers.Screen
import shelfify.ui.theme.MainColor

@Composable
fun CategoryBook(
    navController: NavController,
) {
    Column {
        Box(modifier = Modifier.padding(start = 20.dp, top = 30.dp)) {
            Text(text = "Category", color = MainColor, fontWeight = FontWeight.Bold)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(top = 5.dp)
        ) {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                verticalArrangement = Arrangement.spacedBy(30.dp),
                modifier = Modifier.align(Alignment.Center)
            ) {
                items(
                    CategoryData.categories.take(5) + DataCategoryBook(
                        "See All",
                        R.drawable.allcategory
                    )
                ) { category ->
                    CategoryItem(
                        category = category,
                        onClick = {
                            if (category.name == "See All") {
                                navController.navigate(Screen.Member.SearchScreen.route)
                            } else {
                                navController.navigate("book/${Uri.encode(category.name)}")
                            }
                        }
                    )
                }
            }
        }
    }
}

