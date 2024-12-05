package shelfify.ui.homeScreen.allCategoryBookScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import shelfify.ui.library.categoryBook.FullCategoryBook


class ShowAllCategoryBookScreen {
    @Composable
    fun AllCategoryBook(navController: NavController) {
        Scaffold(
            content = { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    FullCategoryBook(navController = navController)
                }
            },
        )
    }

}
