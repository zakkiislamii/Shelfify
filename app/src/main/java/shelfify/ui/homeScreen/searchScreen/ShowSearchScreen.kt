package shelfify.ui.homeScreen.searchScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import shelfify.ui.components.SearchBar
import shelfify.ui.homeScreen.home.categoryBook.FullCategoryBook


class ShowSearchScreen {
    @Composable
    fun SearchScreen(navController: NavController) {
        Scaffold(
            topBar = {
                SearchBar(onSearch = { query ->
                    println("Search query: $query")
                })
            },
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    FullCategoryBook(navController = navController)
                }
            },
        )
    }

}
