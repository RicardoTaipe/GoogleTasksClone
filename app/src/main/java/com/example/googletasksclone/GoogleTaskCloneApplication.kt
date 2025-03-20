package com.example.googletasksclone

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.googletasksclone.data.preferences.UserPreferencesRepository
import com.example.googletasksclone.data.preferences.UserPreferencesRepositoryImp
import dagger.hilt.android.HiltAndroidApp

private const val USER_PREFERENCE_NAME = "user_preferences"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCE_NAME)

@HiltAndroidApp
class GoogleTaskCloneApplication : Application() {
    lateinit var userPreferencesRepository: UserPreferencesRepository
    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepositoryImp(dataStore)
    }
}