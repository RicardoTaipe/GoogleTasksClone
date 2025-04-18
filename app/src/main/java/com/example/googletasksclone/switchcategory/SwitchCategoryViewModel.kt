package com.example.googletasksclone.switchcategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.googletasksclone.PreferencesMock
import com.example.googletasksclone.data.Category
import com.example.googletasksclone.data.source.CategoryRepository
import com.example.googletasksclone.data.Result.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SwitchCategoryViewModel @Inject constructor(categoryRepository: CategoryRepository) :
    ViewModel() {

    val items: LiveData<List<Category>> = categoryRepository.observeCategories().map {
        if (it is Success) {
            it.data
        } else {
            emptyList()
        }
    }


    private val _selectedItem = MutableLiveData<Category?>(PreferencesMock.selectedList)
    val selectedItem: LiveData<Category?> get() = _selectedItem

    fun selectItem(item: Category) {
        //TODO save item in shared preferences
        PreferencesMock.selectedList = item
        _selectedItem.value = item
    }
}