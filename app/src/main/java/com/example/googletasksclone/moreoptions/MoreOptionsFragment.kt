package com.example.googletasksclone.moreoptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.googletasksclone.databinding.FragmentMoreOptionsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MoreOptionsFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentMoreOptionsBinding? = null
    private val binding get() = _binding!!
    var onListItemSelected: (() -> Unit)? = null

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

            }
            deleteList.setOnClickListener {

            }
            deleteCompletedTasks.setOnClickListener {

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