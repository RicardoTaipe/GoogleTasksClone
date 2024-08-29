package com.example.googletasksclone.switchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.googletasksclone.databinding.FragmentSwitchListBinding
import com.example.googletasksclone.newlist.NewListFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SwitchListFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSwitchListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwitchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            starredList.setOnClickListener {
                //TODO add listener to the activity

            }
            newList.setOnClickListener {
                navigateToNewListFragment()
            }
        }
    }

    private fun navigateToNewListFragment() {
        NewListFragment().show(parentFragmentManager, NewListFragment.TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG: String = this::class.java.simpleName
    }
}