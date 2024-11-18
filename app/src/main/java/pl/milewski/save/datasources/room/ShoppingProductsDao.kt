package pl.milewski.save.datasources.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.milewski.save.models.Product

@Dao
interface ShoppingProductsDao {
    @Query("SELECT * FROM product")
    fun getAll(): Flow<List<Product>>

    @Query("""SELECT * FROM product WHERE name LIKE :name LIMIT 1""")
    fun findByName(name: String): Flow<Product>

    @Insert
    suspend fun insert(product: Product)

    @Query("DELETE FROM product")
    suspend fun deleteAll()
}