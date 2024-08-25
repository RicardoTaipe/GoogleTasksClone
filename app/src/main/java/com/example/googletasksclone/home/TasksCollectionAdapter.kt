package com.example.googletasksclone.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.googletasksclone.starred.StarredTasksFragment
import com.example.googletasksclone.tasks.TasksFragment


class TasksCollectionAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    private val fragmentList = mutableListOf<Fragment>()
    override fun getItemCount(): Int = fragmentList.size

    init {
        fragmentList.apply {
            add(StarredTasksFragment())
            add(TasksFragment())
            add(TasksFragment())
        }
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    // Method to add a fragment
    fun addFragment(fragment: TasksFragment) {
        fragmentList.add(fragment)
        notifyItemInserted(fragmentList.size - 1)
    }

    // Method to remove a fragment
    fun removeFragment(position: Int) {
        if (position in 0 until fragmentList.size) {
            fragmentList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    // Method to replace a fragment at a specific position
    fun replaceFragment(position: Int, fragment: TasksFragment) {
        if (position in 0 until fragmentList.size) {
            fragmentList[position] = fragment
            notifyItemChanged(position)
        }
    }

    // Method to update the entire list of fragments
    fun setFragments(fragments: List<TasksFragment>) {
        fragmentList.clear()
        fragmentList.addAll(fragments)
        notifyDataSetChanged()
    }
}
