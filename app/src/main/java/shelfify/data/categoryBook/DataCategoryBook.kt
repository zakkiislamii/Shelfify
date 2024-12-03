package shelfify.data.categoryBook

import com.example.shelfify.R

data class DataCategoryBook(
    val name: String,
    val iconRes: Int = R.drawable.icon1,
)

object CategoryData {
    val categories = arrayOf(
        DataCategoryBook("Action & Adventure", R.drawable.icon1),
        DataCategoryBook("Antiques & Collectibles", R.drawable.icon2),
        DataCategoryBook("Business & Economics", R.drawable.icon3),
        DataCategoryBook("Computer", R.drawable.icon4),
        DataCategoryBook("Engineering", R.drawable.icon5),
        DataCategoryBook("Horror", R.drawable.icon6),
        DataCategoryBook("Family & Relationship", R.drawable.icon7),
        DataCategoryBook("Love & Romance", R.drawable.icon9),
        DataCategoryBook("See All", R.drawable.allcategory),
    )
}