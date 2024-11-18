package pl.milewski.save.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo val name: String?,
    @ColumnInfo val bought: Boolean?,
    @ColumnInfo(name = "last_time_updated") val lastTimeUpdated: Long?
)