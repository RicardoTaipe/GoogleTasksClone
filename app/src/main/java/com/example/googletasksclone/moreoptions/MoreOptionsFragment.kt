package com.example.googletasksclone.moreoptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.googletasksclone.databinding.FragmentMoreOptionsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

sealed interface MoreOptionsEvent {
    data object RenameList : MoreOptionsEvent
    data object DeleteList : MoreOptionsEvent
    data object DeleteAllCompletedTasks : MoreOptionsEvent
}

@AndroidEntryPoint
class MoreOptionsFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentMoreOptionsBinding? = null
    private val binding get() = _binding!!
    var onListItemSelected: ((event: MoreOptionsEvent) -> Unit)? = null
    private lateinit var categoryId: String
    private var isFavorite: Boolean = false
    private val viewModel by viewModels<MoreOptionsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryId = arguments?.getString(CATEGORY_ID).orEmpty()
        isFavorite = arguments?.getBoolean(FAVORITE_KEY) ?: false

        binding.apply {
            renameList.isEnabled = !isFavorite
            renameList.setOnClickListener {
                onListItemSelected?.invoke(MoreOptionsEvent.RenameList)
            }

            deleteList.isEnabled = !isFavorite
            deleteList.setOnClickListener {
                viewModel.deleteCategory(categoryId)
                onListItemSelected?.invoke(MoreOptionsEvent.DeleteList)
                dismiss()
            }

            deleteCompletedTasks.setOnClickListener {
                onListItemSelected?.invoke(MoreOptionsEvent.DeleteAllCompletedTasks)
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
        const val CATEGORY_ID = "CATEGORY_ID"
        const val FAVORITE_KEY = "FAVORITE_KEY"
    }
}