package shelfify.utils.factory

import shelfify.contracts.card.CardComponent
import shelfify.data.BaseUI
import shelfify.data.BookUI
import shelfify.ui.components.BookCard
import kotlin.reflect.KClass

object CardComponentFactory {
    private val cardMap = mutableMapOf<KClass<out BaseUI>, CardComponent<out BaseUI>>()

    init {
        // Mendaftarkan card component default
        cardMap[BookUI::class] = BookCard()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : BaseUI> create(type: KClass<out T>): CardComponent<T> {
        return (cardMap[type] as? CardComponent<T>)
            ?: throw IllegalArgumentException("Unsupported card type: ${type.simpleName}")
    }

    // Metode untuk mendaftarkan card component baru secara dinamis
    fun <T : BaseUI> register(type: KClass<out T>, cardComponent: CardComponent<T>) {
        cardMap[type] = cardComponent
    }
}