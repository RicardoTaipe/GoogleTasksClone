package com.example.googletasksclone.switchcategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.googletasksclone.R
import com.example.googletasksclone.databinding.FragmentSwitchListBinding
import com.example.googletasksclone.utils.dpToPx
import com.example.googletasksclone.newcategory.NewCategoryFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

sealed interface SwitchEvent {
    data class ItemSelected(val id: String) : SwitchEvent
}

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
            onListItemSelected?.invoke(SwitchEvent.ItemSelected(item.id))
            listsAdapter.notifyItemChanged(listsAdapter.currentList.indexOfFirst { model -> model.id == item.id })
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
        NewCategoryFragment().show(parentFragmentManager, NewCategoryFragment.TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG: String = this::class.java.simpleName
    }
}