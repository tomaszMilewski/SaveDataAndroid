package pl.milewski.save.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val TEST_KEY = stringPreferencesKey("TEST_KEY")
private const val USER_PREFERENCES_NAME = "user_preferences"

internal val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

class DataStoreRepository(private val dataStore: DataStore<Preferences>) {

    fun getValueFlow(): Flow<String> = dataStore.data
        .map { preferences ->
            preferences[TEST_KEY].orEmpty()
        }

    suspend fun updateValue(value: String) {
        dataStore.edit { preferences ->
            preferences[TEST_KEY] = value
        }
    }
}