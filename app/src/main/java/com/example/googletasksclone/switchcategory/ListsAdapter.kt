package com.example.googletasksclone.switchcategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.googletasksclone.R
import com.example.googletasksclone.data.Category
import com.example.googletasksclone.databinding.ListItemLayoutBinding

private const val FAVORITE_LIST_POSITION = 0

class ListsAdapter : ListAdapter<Category, ListsAdapter.ViewHolder>(ListModelDiffCallback()) {
    var onListItemSelected: ((item: Category) -> Unit)? = null
    var selectedItem: Category? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onListItemSelected, item == selectedItem)
    }

    class ViewHolder private constructor(private val binding: ListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Category, onListItemSelected: ((item: Category) -> Unit)?, isSelected: Boolean) {
            if (adapterPosition == FAVORITE_LIST_POSITION) {
                binding.icon.isVisible = true
                binding.icon.setImageResource(if (isSelected) R.drawable.ic_star_24 else R.drawable.ic_star_outline_24)
                binding.title.setText(R.string.starred)
            } else {
                binding.icon.isInvisible = !isSelected
                binding.title.text = item.name
            }
            binding.root.setOnClickListener {
                onListItemSelected?.invoke(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class ListModelDiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}