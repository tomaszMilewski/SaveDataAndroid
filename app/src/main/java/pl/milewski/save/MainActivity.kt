package pl.milewski.save

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import pl.milewski.save.datasources.SQLiteHelper
import pl.milewski.save.datasources.room.ShoppingListDatabase
import pl.milewski.save.repositories.DataStoreRepository
import pl.milewski.save.repositories.ProductsRoomRepository
import pl.milewski.save.repositories.ProductsSQLiteRepository
import pl.milewski.save.repositories.dataStore
import pl.milewski.save.ui.theme.SaveDataTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runAndroidSaveRead()
        enableEdgeToEdge()
        setContent {
            SaveDataTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(name = "Android", modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    private fun runAndroidSaveRead() {
        val dataStoreRepository = DataStoreRepository(dataStore = dataStore)
        val sqliteRepositoryProducts = ProductsSQLiteRepository(sqlSQLiteHelper = SQLiteHelper(this))

        val db = Room.databaseBuilder(applicationContext, ShoppingListDatabase::class.java, "database-name").build()
        val productsRoomRepository = ProductsRoomRepository(shoppingListDatabase = db)

        val key = "key"
        saveToSharedPreferences(key, "test")
        Log.d(this::class.java.simpleName, "Saved value: ${readFromSharedPreferences(key)}")

        sqliteRepositoryProducts.clearList()
        sqliteRepositoryProducts.addProduct("Ser")
        sqliteRepositoryProducts.addProduct("Szynka")
        sqliteRepositoryProducts.addProduct("Chleb")
        sqliteRepositoryProducts.getProducts().forEach { product -> Log.d(this::class.java.simpleName, "SQLite product: $product") }

        CoroutineScope(Dispatchers.IO).launch {
            dataStoreRepository.updateValue("asdasdasd123123")
            dataStoreRepository.getValueFlow().first().let { value -> Log.d(this@MainActivity::class.java.simpleName, "DataStore value: $value") }

            productsRoomRepository.clearList()
            productsRoomRepository.addProduct("Ser")
            productsRoomRepository.addProduct("Szynka")
            productsRoomRepository.addProduct("Chleb")
            productsRoomRepository.getProducts().first().let { products -> products.forEach { product ->
                Log.d(this@MainActivity::class.java.simpleName, "Room product: $product")
            } }
        }
    }

    private fun readFromSharedPreferences(key: String): String? = getSharedPreferencesInstance().getString(key, null)

    private fun saveToSharedPreferences(key: String, value: String) {
        getSharedPreferencesInstance().edit().apply {
            putString(key, value)
            apply()
        }
    }

    private fun getSharedPreferencesInstance() = this.getSharedPreferences("packageName", Activity.MODE_PRIVATE)
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SaveDataTheme { Greeting("Android") }
}
