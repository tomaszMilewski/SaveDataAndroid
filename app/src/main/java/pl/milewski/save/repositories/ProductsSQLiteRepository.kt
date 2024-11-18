package pl.milewski.save.repositories

import pl.milewski.save.datasources.SQLiteHelper

class ProductsSQLiteRepository(private val sqlSQLiteHelper: SQLiteHelper) {
    fun addProduct(name: String) {
        sqlSQLiteHelper.addProduct(name)
    }

    fun getProducts() = sqlSQLiteHelper.getProducts()

    fun clearList() = sqlSQLiteHelper.deleteProducts()
}
