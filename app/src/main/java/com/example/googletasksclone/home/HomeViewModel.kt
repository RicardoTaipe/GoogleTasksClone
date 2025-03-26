package com.example.googletasksclone.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.googletasksclone.data.Category
import com.example.googletasksclone.data.source.CategoryRepository
import com.example.todoapp.data.Result.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val categoryRepository: CategoryRepository) :
    ViewModel() {
    val categories: LiveData<List<Category>> = categoryRepository.observeCategories().map {
        if (it is Success) {
            it.data + listOf(Category(name = "New List"))
        } else {
            emptyList()
        }
    }

    fun prepopulateDatabase() {
        viewModelScope.launch {
            categoryRepository.saveCategory(Category("Favorite", true, "favoriteId"))
        }
    }
}