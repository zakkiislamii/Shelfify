package shelfify.data

import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem {
    abstract val title: String

    data class IconItem(
        override val title: String,
        val icon: ImageVector,
    ) : NavItem()

    data class DrawableItem(
        override val title: String,
        val drawable: Int,
    ) : NavItem()
}
