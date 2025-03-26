package com.example.googletasksclone.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.googletasksclone.data.Category
import com.example.googletasksclone.tasks.TasksFragment
import com.example.googletasksclone.tasks.TasksFragment.Companion.CATEGORY_ID


class TasksCollectionAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    var categories: List<Category> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment {
        val fragment = TasksFragment()
        fragment.arguments = Bundle().apply {
            putString(CATEGORY_ID, categories[position].id)
        }
        return fragment
    }
}
