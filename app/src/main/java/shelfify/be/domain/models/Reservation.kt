package shelfify.be.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import shelfify.contracts.enumerations.Status
import java.util.Date

@Entity(
    tableName = "Reservations",
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
        )
    ]
)

data class Reservation(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "reservation_id")
    val reservationId: Int = 0,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "book_id")
    val bookId: Int,

    @ColumnInfo(name = "status")
    val status: Status = Status.PENDING,

    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Date = Date(),
)