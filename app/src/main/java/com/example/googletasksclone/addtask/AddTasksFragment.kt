package com.example.googletasksclone.addtask

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.example.googletasksclone.R
import com.example.googletasksclone.databinding.FragmentAddTasksBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class AddTasksFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddTasksBinding? = null
    private val binding get() = _binding!!
    private lateinit var behavior: BottomSheetBehavior<FrameLayout>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTasksBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                showDiscardConfirmationDialog()
                return@setOnKeyListener true
            } else {
                return@setOnKeyListener false
            }
        }
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val scrim =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.touch_outside)

            scrim?.setOnClickListener {
                showDiscardConfirmationDialog()
            }
            behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState in setOf(
                            BottomSheetBehavior.STATE_COLLAPSED,
                            BottomSheetBehavior.STATE_HIDDEN,
                            BottomSheetBehavior.STATE_SETTLING
                        )
                    ) {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (slideOffset in 0f..0.5f) {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                        showDiscardConfirmationDialog()
                    }
                }
            })
        }

        return dialog
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            details.setOnClickListener {
                detailsTextField.apply {
                    isVisible = true
                    editText?.requestFocus()
                }
            }
            detailsTextField.editText?.apply {
                doOnTextChanged { text, _, _, _ ->
                    saveButton.isEnabled = text.toString().trim().isNotBlank()
                }
            }
            titleTextField.editText?.apply {
                requestFocus()
                doOnTextChanged { text, _, _, _ ->
                    saveButton.isEnabled = text.toString().trim().isNotBlank()

                }
                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        handleDoneAction()
                        true // Return true if the event is consumed
                    } else {
                        false // Return false to allow other handlers to process the event
                    }
                }
            }
        }
    }


    private fun handleDoneAction() {
    }

    private fun showDiscardConfirmationDialog() {
        val title = binding.titleTextField.editText?.text?.toString().orEmpty()
        val details = binding.detailsTextField.editText?.text?.toString().orEmpty()
        if (title.isNotBlank() || details.isNotBlank()) {
            MaterialAlertDialogBuilder(requireActivity()).setTitle(getString(R.string.discard_current_task))
                .setMessage(R.string.discard_task_description)
                .setCancelable(false)
                .setPositiveButton(R.string.discard) { _, _ ->
                    dismiss()
                }.setNegativeButton(R.string.cancel, null).show()
        } else {
            dismiss()
        }
    }


    companion object {
        val TAG: String = this::class.java.simpleName
    }
}