package com.example.googletasksclone.sort

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.googletasksclone.data.preferences.SortOrder
import com.example.googletasksclone.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.launch

class SortViewModel(private val userPreferencesRepository: UserPreferencesRepository) :
    ViewModel() {

    val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow.asLiveData()

    fun setSortOption(sortOrder: SortOrder) {
        viewModelScope.launch { userPreferencesRepository.saveSortOption(sortOrder) }
    }
}