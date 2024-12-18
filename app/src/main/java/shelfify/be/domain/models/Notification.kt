package shelfify.be.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import shelfify.contracts.enumerations.Status
import java.util.Date

@Entity(
    tableName = "Notifications",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Reservation::class,
            parentColumns = ["reservation_id"],
            childColumns = ["reservation_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["reservation_id"])
    ]
)
data class Notification(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "notification_id")
    val notificationId: Int = 0,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "reservation_id")
    val reservationId: Int,

    @ColumnInfo(name = "status_notification")
    val statusNotification: Status,

    @ColumnInfo(name = "message")
    val message: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date(),
)