package com.example.googletasksclone.addeditcategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.googletasksclone.data.Category
import com.example.googletasksclone.data.source.CategoryRepository
import com.example.googletasksclone.utils.Event
import com.example.todoapp.data.Result.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditCategoryViewModel @Inject constructor(private val categoryRepository: CategoryRepository) :
    ViewModel() {
    val name = MutableLiveData<String>()

    val isDoneButtonEnabled = name.map { it.trim().isNotBlank() }

    private var categoryId: String? = null

    private var isNewCategory: Boolean = false
    private var isDataLoaded = false

    private val _categoryUpdatedEvent = MutableLiveData<Event<Unit>>()
    val categoryUpdatedEvent: LiveData<Event<Unit>> = _categoryUpdatedEvent


    fun start(categoryId: String?) {
        this.categoryId = categoryId
        if (categoryId.isNullOrBlank()) {
            // No need to populate, it's a new task
            isNewCategory = true
            return
        }
        if (isDataLoaded) {
            // No need to populate, already have data.
            return
        }

        isNewCategory = false
        viewModelScope.launch {
            categoryRepository.getCategory(categoryId).let { result ->
                if (result is Success) {
                    name.value = result.data.name
                }
            }
        }

    }

    fun saveCategory() {
        val currentName = name.value
        if (currentName.isNullOrBlank()) {
            return
        }

        if (isNewCategory || categoryId.isNullOrBlank()) {
            createCategory(Category(currentName))
        } else {
            val category = Category(id = categoryId.orEmpty(), name = currentName)
            updateCategory(category)
        }
    }

    private fun createCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.saveCategory(category)
            _categoryUpdatedEvent.value = Event(Unit)
        }
    }

    private fun updateCategory(category: Category) {
        if (isNewCategory) {
            throw RuntimeException("updateCategory() was called but category is new.")
        }
        viewModelScope.launch {
            categoryRepository.saveCategory(category)
            _categoryUpdatedEvent.value = Event(Unit)
        }
    }
}