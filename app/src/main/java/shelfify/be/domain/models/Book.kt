package shelfify.be.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    val bookId: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "writer")
    val writer: String,

    @ColumnInfo(name = "isbn")
    val isbn: String,

    @ColumnInfo(name = "publisher")
    val publisher: String,

    @ColumnInfo(name = "publication_year")
    val publicationYear: Int,

    @ColumnInfo(name = "language")
    val language: String,

    @ColumnInfo(name = "stock")
    val stock: Int = 0,

    @ColumnInfo(name = "pages")
    val pages: Int,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "book_image")
    val bookImage: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Date = Date(),
)