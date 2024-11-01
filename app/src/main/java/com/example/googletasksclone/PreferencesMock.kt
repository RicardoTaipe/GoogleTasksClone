package com.example.googletasksclone

import com.example.googletasksclone.data.ListModel
import com.example.googletasksclone.sort.SortEvent

object PreferencesMock {
    var order: SortEvent = SortEvent.MyOrder
    var selectedList: ListModel? = null
}