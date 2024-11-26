package com.example.shelfify.domain.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shelfify.domain.dao.UserDao
import com.example.shelfify.domain.models.Book
import com.example.shelfify.domain.models.CartEntity
import com.example.shelfify.domain.models.User
import com.example.shelfify.utils.converters.DateConverter
import com.example.shelfify.utils.converters.RoleConverter

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