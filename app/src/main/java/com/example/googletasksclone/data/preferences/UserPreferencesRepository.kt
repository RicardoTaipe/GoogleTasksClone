package com.example.googletasksclone.data.preferences

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


interface UserPreferencesRepository {
    val userPreferencesFlow: Flow<UserPreferences>
    suspend fun saveSortOption(newSortOrder: SortOrder)
}

enum class SortOrder {
    NONE,
    BY_MY_ORDER,
    BY_DATE,
    BY_STARRED
}

data class UserPreferences(val sortOrder: SortOrder)

class UserPreferencesRepositoryImp(private val dataStore: DataStore<Preferences>) :
    UserPreferencesRepository {

    private companion object PreferencesKeys {
        val SORT_ORDER = stringPreferencesKey("sort_order")
        val SHOW_COMPLETED = booleanPreferencesKey("show_completed")
        val TAG: String = this::class.java.simpleName
    }

    /**
     * Get the user preferences flow.
     */
    override val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapUserPreferences(preferences)
        }

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        // Get the sort order from preferences and convert it to a [SortOrder] object
        val sortOrder = SortOrder.valueOf(preferences[SORT_ORDER] ?: SortOrder.NONE.name)
        return UserPreferences(sortOrder)
    }

    override suspend fun saveSortOption(newSortOrder: SortOrder) {
        dataStore.edit { preferences ->
            preferences[SORT_ORDER] = newSortOrder.name
        }
    }
}
// TODO see for reference https://github.com/android/codelab-android-datastore/blob/preferences_datastore/app/src/main/java/com/codelab/android/datastore/data/UserPreferencesRepository.kt