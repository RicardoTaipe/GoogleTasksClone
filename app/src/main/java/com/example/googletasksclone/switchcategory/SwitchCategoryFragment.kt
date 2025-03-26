package com.example.googletasksclone.switchcategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.googletasksclone.PreferencesMock
import com.example.googletasksclone.R
import com.example.googletasksclone.databinding.FragmentSwitchListBinding
import com.example.googletasksclone.utils.dpToPx
import com.example.googletasksclone.addeditcategory.AddEditCategoryFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

sealed interface SwitchEvent {
    data class ItemSelected(val position: Int) : SwitchEvent
}

@AndroidEntryPoint
class SwitchCategoryFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSwitchListBinding? = null
    private val binding get() = _binding!!
    var onListItemSelected: ((event: SwitchEvent) -> Unit)? = null

    private val listsAdapter: ListsAdapter by lazy {
        ListsAdapter()
    }

    private val viewModel by viewModels<SwitchCategoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwitchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpNewListButton()
        initializeAdapter()
        setupRecyclerView()
        observeListModel()
    }

    private fun initializeAdapter() {
        listsAdapter.onListItemSelected = { item ->
            viewModel.selectItem(item)
            val indexPosition = listsAdapter.currentList.indexOfFirst { model -> model.id == item.id }
            onListItemSelected?.invoke(SwitchEvent.ItemSelected(indexPosition))
            listsAdapter.notifyItemChanged(indexPosition)
        }

        viewModel.selectedItem.observe(viewLifecycleOwner) {
            listsAdapter.selectedItem = it
        }
    }

    private fun setUpNewListButton() {
        binding.newList.setOnClickListener = {
            navigateToNewListFragment()
        }
    }

    private fun setupRecyclerView() {
        binding.listsRecyclerview.adapter = listsAdapter
        val dividerHeight = requireContext().dpToPx(1)
        val color = ContextCompat.getColor(requireContext(), R.color.md_theme_onSurfaceVariant)
        binding.listsRecyclerview.addItemDecoration(DividerItemDecoration(dividerHeight, color))
    }

    private fun observeListModel() {
        viewModel.items.observe(viewLifecycleOwner) {
            listsAdapter.submitList(it)
        }
    }

    private fun navigateToNewListFragment() {
        dismiss()
        AddEditCategoryFragment().show(parentFragmentManager, AddEditCategoryFragment.TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG: String = this::class.java.simpleName
    }
}