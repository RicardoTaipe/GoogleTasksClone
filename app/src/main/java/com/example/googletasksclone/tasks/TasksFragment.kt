package com.example.googletasksclone.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.googletasksclone.addtask.AddTasksFragment
import com.example.googletasksclone.databinding.FragmentTasksBinding

class TasksFragment : Fragment() {
    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = TasksFragment()
    }

    private val viewModel: TasksViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addTasksButton.setOnClickListener {
            val modalBottomSheet = AddTasksFragment()
            modalBottomSheet.show(parentFragmentManager, AddTasksFragment::class.java.simpleName)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}