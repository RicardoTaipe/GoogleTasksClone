package com.example.googletasksclone.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.googletasksclone.sort.SortEvent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


interface UserPreferencesRepository {
    // Save the order selection
    suspend fun saveOrder(sortEvent: SortEvent)

    // Retrieve the current order selection
    suspend fun getOrder(): SortEvent
}

class UserPreferencesRepositoryImp(private val dataStore: DataStore<Preferences>) :
    UserPreferencesRepository {

    private companion object PreferencesKeys {
        val SORT_ORDER = stringPreferencesKey("sort_order")
        val SHOW_COMPLETED = booleanPreferencesKey("show_completed")
    }

    // Save the order selection
    override suspend fun saveOrder(sortEvent: SortEvent) {
        dataStore.edit { preferences ->
            preferences[SORT_ORDER] = sortEvent::class.simpleName ?: "MyOrder"
        }
    }

    // Retrieve the current order selection
    override suspend fun getOrder(): SortEvent {
        val orderString = dataStore.data
            .map { preferences ->
                preferences[SORT_ORDER] ?: SortEvent.MyOrder::class.simpleName
            }
            .first()

        return when (orderString) {
            SortEvent.MyOrder::class.simpleName -> SortEvent.MyOrder
            SortEvent.Date::class.simpleName -> SortEvent.Date
            SortEvent.Starred::class.simpleName -> SortEvent.Starred
            else -> SortEvent.MyOrder
        }
    }
}
