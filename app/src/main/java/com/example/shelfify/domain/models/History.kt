package com.example.shelfify.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "Histories",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"]
        ),
        ForeignKey(
            entity = Book::class,
            parentColumns = ["book_id"],
            childColumns = ["book_id"]
        ),
        ForeignKey(
            entity = Reservation::class,
            parentColumns = ["reservation_id"],
            childColumns = ["reservation_id"]
        )
    ]
)
data class History(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "history_id")
    val historyId: Int = 0,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "book_id")
    val bookId: Int,

    @ColumnInfo(name = "reservation_id")
    val reservationId: Int,

    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date(),
)