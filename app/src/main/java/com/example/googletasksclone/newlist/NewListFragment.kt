package com.example.googletasksclone.newlist

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.widget.doOnTextChanged
import com.example.googletasksclone.databinding.FragmentNewListBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewListFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentNewListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            textField.editText?.apply {
                requestFocus()
                doOnTextChanged { text, _, _, _ ->
                    doneButton.isEnabled = text.toString().trim().isNotBlank()
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
            closeIcon.setOnClickListener {
                dismiss()
            }
            doneButton.setOnClickListener {
                handleDoneAction()
            }
        }
    }

    private fun handleDoneAction() {
        //TODO save new list to db
        val title = binding.textField.editText?.text.toString().trim()
        dismiss()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG = NewListFragment::class.java.simpleName
    }
}