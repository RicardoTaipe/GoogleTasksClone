package com.example.googletasksclone.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.googletasksclone.PreferencesMock
import com.example.googletasksclone.customviews.ListItemView
import com.example.googletasksclone.databinding.FragmentSortBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


sealed interface SortEvent {
    data object MyOrder : SortEvent
    data object Date : SortEvent
    data object Starred : SortEvent
}

class SortFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSortBinding? = null
    private val binding get() = _binding!!
    var onListItemSelected: ((event: SortEvent) -> Unit)? = null
    private var selectedOption: SortEvent = PreferencesMock.order

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSortBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpItem(binding.myOrder, SortEvent.MyOrder)
        setUpItem(binding.date, SortEvent.Date)
        setUpItem(binding.starred, SortEvent.Starred)
    }

    private fun setUpItem(
        view: ListItemView,
        event: SortEvent,
    ) {
        view.apply {
            setOnClickListener = {
                onListItemSelected?.invoke(event)
                PreferencesMock.order = event
                dismiss()
            }
            //TODO active the right icon from preferences
            setIconVisibility(selectedOption == event)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG: String = this::class.java.simpleName
    }
}