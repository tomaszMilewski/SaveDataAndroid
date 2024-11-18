package pl.milewski.save.repositories

import kotlinx.coroutines.flow.Flow
import pl.milewski.save.datasources.room.ShoppingListDatabase
import pl.milewski.save.models.Product

class ProductsRoomRepository(private val shoppingListDatabase: ShoppingListDatabase) {
    suspend fun addProduct(name: String) {
        val newProduct = Product(
            name = name,
            bought = false,
            lastTimeUpdated = System.currentTimeMillis()
        )
        shoppingListDatabase.shoppingProductsDao().insert(newProduct)
    }

    fun getProducts(): Flow<List<Product>> = shoppingListDatabase.shoppingProductsDao().getAll()

    suspend fun clearList() = shoppingListDatabase.shoppingProductsDao().deleteAll()
}
