package com.example.googletasksclone.switchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.googletasksclone.PreferencesMock
import com.example.googletasksclone.data.ListModel

class SwitchListsViewModel : ViewModel() {
    val items: LiveData<List<ListModel>> =
        MutableLiveData(List(3) { ListModel(it.toString(), "List $it") })

    private val _selectedItem = MutableLiveData<ListModel?>(PreferencesMock.selectedList)
    val selectedItem: LiveData<ListModel?> get() = _selectedItem

    fun selectItem(item: ListModel) {
        //TODO save item in shared preferences
        PreferencesMock.selectedList = item
        _selectedItem.value = item
    }
}