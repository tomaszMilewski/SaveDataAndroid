package pl.milewski.save.datasources

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import pl.milewski.save.models.ShoppingProduct

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "MyDatabase.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "ShoppingList"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_BOUGHT = "bought"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery =
            """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_BOUGHT INT
            )
        """
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addProduct(name: String) {
        val db = writableDatabase
        val values =
            ContentValues().apply {
                put(COLUMN_NAME, name)
                put(COLUMN_BOUGHT, false)
            }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getProducts(): List<ShoppingProduct> {
        val products = mutableListOf<ShoppingProduct>()
        val db = readableDatabase

        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val bought = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOUGHT)) == 1
                products.add(ShoppingProduct(id, name, bought))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return products
    }

    fun deleteProducts() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null);
        db.close()
    }
}
