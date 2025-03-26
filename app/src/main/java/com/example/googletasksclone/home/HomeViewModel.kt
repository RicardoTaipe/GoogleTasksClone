package com.example.googletasksclone.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.googletasksclone.data.Category
import com.example.googletasksclone.data.source.CategoryRepository
import com.example.todoapp.data.Result
import com.example.todoapp.data.Result.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val categoryRepository: CategoryRepository) :
    ViewModel() {
    val categories: LiveData<List<Category>> = categoryRepository.observeCategories().switchMap {
        processResult(it)
    }

    private fun processResult(categoryResult: Result<List<Category>>): LiveData<List<Category>> {
        val result = MutableLiveData<List<Category>>()
        if (categoryResult is Success) {
            result.value = categoryResult.data + listOf(Category(name = "New List"))
        } else {
            result.value = emptyList()
        }
        return result
    }

    fun prepopulateDatabase() {
        viewModelScope.launch {
            categoryRepository.saveCategory(Category("Favorite", true, "favoriteId"))
        }
    }
}