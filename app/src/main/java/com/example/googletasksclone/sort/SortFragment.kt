package com.example.googletasksclone.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.googletasksclone.customviews.ListItemView
import com.example.googletasksclone.data.preferences.SortOrder
import com.example.googletasksclone.databinding.FragmentSortBinding
import com.example.googletasksclone.utils.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSortBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SortViewModel> { ViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return FragmentSortBinding.inflate(inflater, container, false).run {
            _binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set up items in a loop
        val sortItems = listOf(
            binding.myOrder to SortOrder.BY_MY_ORDER,
            binding.date to SortOrder.BY_DATE,
            binding.starred to SortOrder.BY_STARRED
        )

        sortItems.forEach { (itemView, sortOrder) ->
            setUpItem(itemView, sortOrder)
        }

        viewModel.userPreferencesFlow.observe(viewLifecycleOwner) { userPreference ->
            sortItems.forEach { (itemView, sortOrder) ->
                itemView.setIconVisibility(sortOrder === userPreference.sortOrder)
            }
        }
    }

    private fun setUpItem(view: ListItemView, sortOrder: SortOrder) {
        view.apply {
            setOnClickListener = {
                viewModel.setSortOption(sortOrder)
                dismiss()
            }
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