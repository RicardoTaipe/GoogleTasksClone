package com.example.googletasksclone.switchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.example.googletasksclone.R
import com.example.googletasksclone.databinding.FragmentSwitchListBinding
import com.example.googletasksclone.databinding.ListItemLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

sealed interface SwitchEvent {
    data class ItemSelected(val id: String) : SwitchEvent
}

class SwitchListFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSwitchListBinding? = null
    private val binding get() = _binding!!
    var onListItemSelected: ((event: SwitchEvent) -> Unit)? = null
    private lateinit var listsAdapter: ListsAdapter

    private val viewModel by viewModels<SwitchListsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwitchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpItem(binding.starredList, R.drawable.ic_star_outline_24, R.string.starred)
        setUpItem(binding.newList, R.drawable.ic_add_24, R.string.create_new_list)

        binding.run {
            starredList.root.setOnClickListener {
                //TODO active the selected item on any lists
                onListItemSelected?.invoke(SwitchEvent.ItemSelected("0"))
                dismiss()
            }
            newList.root.setOnClickListener {
                navigateToNewListFragment()
            }
        }
        listsAdapter = ListsAdapter()
        listsAdapter.onListItemSelected = onListItemSelected
        binding.listsRecyclerview.adapter = listsAdapter
        observeListModel()
    }

    private fun setUpItem(
        view: ListItemLayoutBinding, @DrawableRes iconDrawable: Int, @StringRes text: Int
    ) {
        view.apply {
            icon.setImageResource(iconDrawable)
            title.setText(text)
        }
    }

    private fun observeListModel() {
        viewModel.items.observe(viewLifecycleOwner) {
            listsAdapter.submitList(it)
        }
    }

    private fun navigateToNewListFragment() {
        dismiss()
        //NewListFragment().show(parentFragmentManager, NewListFragment.TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG: String = this::class.java.simpleName
    }
}