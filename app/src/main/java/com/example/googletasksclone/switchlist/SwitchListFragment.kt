package com.example.googletasksclone.switchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.googletasksclone.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SwitchListFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_switch_list, container, false)
    }

    companion object {
        val TAG: String = this::class.java.simpleName
    }
}