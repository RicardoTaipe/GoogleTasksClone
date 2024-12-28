package com.example.googletasksclone.sort

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.googletasksclone.sort.SortPreferencesManager.PreferencesKeys.SORT_ORDER
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SortPreferencesManager(private val dataStore: DataStore<Preferences>) {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sort_preferences")

    private object PreferencesKeys {
        val SORT_ORDER = stringPreferencesKey("sort_order")
        val SHOW_COMPLETED = booleanPreferencesKey("show_completed")
    }
//    companion object {
//        val ORDER_KEY = preferencesKey<String>("order_key")
//    }

    // Save the order selection
    suspend fun saveOrder(sortEvent: SortEvent) {
        dataStore.edit { preferences ->
            preferences[SORT_ORDER] = sortEvent::class.simpleName ?: "MyOrder"
        }
    }

    // Retrieve the current order selection
    suspend fun getOrder(): SortEvent {
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
