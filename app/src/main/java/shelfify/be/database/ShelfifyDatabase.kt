package shelfify.be.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import shelfify.be.dao.BookDao
import shelfify.be.dao.CartDao
import shelfify.be.dao.UserDao
import shelfify.be.domain.models.Book
import shelfify.be.domain.models.CartEntity
import shelfify.be.domain.models.User
import shelfify.utils.converter.DateConverter

@Database(
    entities = [
        User::class,
        CartEntity::class,
        Book::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateConverter::class,
)
abstract class ShelfifyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: ShelfifyDatabase? = null

        fun getInstance(context: Context): ShelfifyDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): ShelfifyDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ShelfifyDatabase::class.java,
                "Shelfify.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}