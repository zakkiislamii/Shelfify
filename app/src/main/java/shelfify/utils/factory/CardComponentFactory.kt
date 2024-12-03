package shelfify.utils.factory

import shelfify.contracts.card.CardBookComponent
import shelfify.data.baseUI.BookDataBaseUI
import shelfify.data.dataMapping.BookUI
import shelfify.ui.components.card.BookCard
import kotlin.reflect.KClass

object CardBookComponentFactory {
    private val cardMap =
        mutableMapOf<KClass<out BookDataBaseUI>, CardBookComponent<out BookDataBaseUI>>()

    init {
        // Mendaftarkan card component default
        cardMap[BookUI::class] = BookCard()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : BookDataBaseUI> create(type: KClass<out T>): CardBookComponent<T> {
        return (cardMap[type] as? CardBookComponent<T>)
            ?: throw IllegalArgumentException("Unsupported card type: ${type.simpleName}")
    }

    // Metode untuk mendaftarkan card component baru secara dinamis
    fun <T : BookDataBaseUI> register(
        type: KClass<out T>,
        CardBookComponent: CardBookComponent<T>,
    ) {
        cardMap[type] = CardBookComponent
    }
}