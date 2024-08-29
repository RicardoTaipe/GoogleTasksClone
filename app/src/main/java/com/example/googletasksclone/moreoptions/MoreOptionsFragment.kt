package com.example.googletasksclone.moreoptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.googletasksclone.databinding.FragmentMoreOptionsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

sealed interface MoreOptionsEvent {
    data object RenameList : MoreOptionsEvent
    data object DeleteList : MoreOptionsEvent
    data object DeleteAllCompletedTasks : MoreOptionsEvent
}

class MoreOptionsFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentMoreOptionsBinding? = null
    private val binding get() = _binding!!
    var onListItemSelected: ((event: MoreOptionsEvent) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            renameList.setOnClickListener {
                onListItemSelected?.invoke(MoreOptionsEvent.RenameList)
            }
            deleteList.setOnClickListener {
                onListItemSelected?.invoke(MoreOptionsEvent.DeleteList)
            }
            deleteCompletedTasks.setOnClickListener {
                onListItemSelected?.invoke(MoreOptionsEvent.DeleteAllCompletedTasks)
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