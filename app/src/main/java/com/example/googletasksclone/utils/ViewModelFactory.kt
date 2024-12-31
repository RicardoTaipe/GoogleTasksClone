package com.example.googletasksclone.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.googletasksclone.GoogleTaskCloneApplication
import com.example.googletasksclone.newlist.NewListViewModel
import com.example.googletasksclone.sort.SortViewModel

/**
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
val TodoViewModelFactory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        with(modelClass) {
            val application = checkNotNull(extras[APPLICATION_KEY]) as GoogleTaskCloneApplication
            //val tasksRepository = application.taskRepository
            when {
                isAssignableFrom(NewListViewModel::class.java) ->
                    NewListViewModel()
                isAssignableFrom(SortViewModel::class.java) ->
                    SortViewModel()
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}