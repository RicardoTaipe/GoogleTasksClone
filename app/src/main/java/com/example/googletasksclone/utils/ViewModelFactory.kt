package com.example.googletasksclone.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.googletasksclone.GoogleTaskCloneApplication
import com.example.googletasksclone.sort.SortViewModel

/**
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
val ViewModelFactory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        with(modelClass) {
            val application = checkNotNull(extras[APPLICATION_KEY]) as GoogleTaskCloneApplication
            when {
                isAssignableFrom(SortViewModel::class.java) ->
                    SortViewModel(application.userPreferencesRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}