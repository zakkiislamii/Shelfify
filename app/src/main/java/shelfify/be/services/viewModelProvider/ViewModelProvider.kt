package shelfify.be.services.viewModelProvider

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import shelfify.be.database.ShelfifyDatabase
import shelfify.be.domain.repositories.BookRepository
import shelfify.be.domain.repositories.CartRepository
import shelfify.be.domain.repositories.HistoryRepository
import shelfify.be.domain.repositories.NotificationRepository
import shelfify.be.domain.repositories.ReservationRepository
import shelfify.be.domain.repositories.UserRepository
import shelfify.be.services.viewModelFactory.ViewModelFactory
import shelfify.routers.DataViewModel

class ViewModelProvider(context: Context) {
    private val database = ShelfifyDatabase.getInstance(context)
    private val viewModelFactory = ViewModelFactory(
        UserRepository(database.userDao()),
        BookRepository(database.bookDao()),
        CartRepository(database.cartDao()),
        NotificationRepository(database.notificationDao()),
        ReservationRepository(database.reservationDao()),
        HistoryRepository(database.historyDao())
    )

    @Composable
    fun createViewModels(owner: ViewModelStoreOwner) = DataViewModel(
        authViewModel = viewModel(owner, factory = viewModelFactory),
        bookViewModel = viewModel(owner, factory = viewModelFactory),
        memberViewModel = viewModel(owner, factory = viewModelFactory),
        cartViewModel = viewModel(owner, factory = viewModelFactory),
        historyViewModel = viewModel(owner, factory = viewModelFactory),
        reservationViewModel = viewModel(owner, factory = viewModelFactory),
        notificationViewModel = viewModel(owner, factory = viewModelFactory),
        adminViewModel = viewModel(owner, factory = viewModelFactory)
    )
}