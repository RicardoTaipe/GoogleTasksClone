package com.example.googletasksclone.moreoptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googletasksclone.data.source.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreOptionsViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    fun deleteCategory(categoryId: String) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(categoryId)
        }
    }
}
