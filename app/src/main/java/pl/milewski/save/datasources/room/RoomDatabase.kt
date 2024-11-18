package pl.milewski.save.datasources.room

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.milewski.save.models.Product

@Database(entities = [Product::class], version = 1)
abstract class ShoppingListDatabase : RoomDatabase() {
    abstract fun shoppingProductsDao(): ShoppingProductsDao
}