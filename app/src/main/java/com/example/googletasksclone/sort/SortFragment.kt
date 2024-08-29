package com.example.googletasksclone.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.view.isInvisible
import com.example.googletasksclone.R
import com.example.googletasksclone.databinding.FragmentSortBinding
import com.example.googletasksclone.databinding.ListItemLayoutBinding
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
    private var selectedOption: SortEvent = SortEvent.MyOrder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSortBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpItem(binding.myOrder, R.string.my_order, SortEvent.MyOrder)
        setUpItem(binding.date, R.string.date, SortEvent.Date)
        setUpItem(binding.starred, R.string.starred_recently, SortEvent.Starred)
    }

    private fun setUpItem(
        view: ListItemLayoutBinding,
        @StringRes text: Int,
        event: SortEvent,
    ) {
        view.apply {
            title.setText(text)
            root.setOnClickListener {
                onListItemSelected?.invoke(event)
                selectedOption = event
                dismiss()
            }
            //TODO active the right icon from preferences
            icon.isInvisible = selectedOption !== event
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