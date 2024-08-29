package com.example.googletasksclone.starred

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.googletasksclone.data.ListModel
class SwitchListsViewModel : ViewModel() {
    val items: LiveData<List<ListModel>> =
        MutableLiveData(List(3) { ListModel(it.toString(), "List $it") })
}