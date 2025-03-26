package com.example.googletasksclone.addeditcategory

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import com.example.googletasksclone.R
import com.example.googletasksclone.databinding.FragmentNewListBinding
import com.example.googletasksclone.utils.EventObserver
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditCategoryFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewListBinding
    private var categoryId: String = ""

    private val viewModel by viewModels<AddEditCategoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryId = it.getString(CATEGORY_ID).orEmpty()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewListBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        if (categoryId.isNotBlank()) {
            binding.title.setText(R.string.rename_list)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            textField.editText?.apply {
                requestFocus()
                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (text.isNotBlank()) handleDoneAction()
                        clearFocus()
                        true // Return true if the event is consumed
                    } else {
                        false // Return false to allow other handlers to process the event
                    }
                }
            }
            closeIcon.setOnClickListener {
                dismiss()
            }
            doneButton.setOnClickListener {
                handleDoneAction()
            }
        }
        viewModel.start(categoryId)

        viewModel.categoryUpdatedEvent.observe(viewLifecycleOwner, EventObserver {
            dismiss()
        })
    }

    private fun handleDoneAction() {
        viewModel.saveCategory()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return (super.onCreateDialog(savedInstanceState) as BottomSheetDialog).apply {
            setOnShowListener { dialog ->
                val bottomSheetDialog = dialog as BottomSheetDialog
                val bottomSheet =
                    bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet?.let {
                    BottomSheetBehavior.from(it).apply {
                        state = BottomSheetBehavior.STATE_EXPANDED
                        isDraggable = false
                        it.layoutParams = it.layoutParams.apply {
                            height = WindowManager.LayoutParams.MATCH_PARENT
                        }
                    }
                }
            }
        }
    }

    companion object {
        val TAG: String = AddEditCategoryFragment::class.java.simpleName
        private const val CATEGORY_ID = "CATEGORY_ID"

        @JvmStatic
        fun newInstance(categoryId: String) =
            AddEditCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_ID, categoryId)
                }
            }
    }
}