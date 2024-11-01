package com.example.googletasksclone.switchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.googletasksclone.R
import com.example.googletasksclone.data.ListModel
import com.example.googletasksclone.databinding.ListItemLayoutBinding

private const val FAVORITE_LIST_POSITION = 0

class ListsAdapter : ListAdapter<ListModel, ListsAdapter.ViewHolder>(ListModelDiffCallback()) {
    var onListItemSelected: ((item: ListModel) -> Unit)? = null
    var selectedItem: ListModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onListItemSelected, item == selectedItem)
    }

    class ViewHolder private constructor(private val binding: ListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ListModel, onListItemSelected: ((item: ListModel) -> Unit)?, isSelected: Boolean) {
            if (adapterPosition == FAVORITE_LIST_POSITION) {
                binding.icon.isVisible = true
                binding.icon.setImageResource(if (isSelected) R.drawable.ic_star_24 else R.drawable.ic_star_outline_24)
                binding.title.setText(R.string.starred)
            } else {
                binding.icon.isInvisible = !isSelected
                binding.title.text = item.title
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

class ListModelDiffCallback : DiffUtil.ItemCallback<ListModel>() {
    override fun areItemsTheSame(oldItem: ListModel, newItem: ListModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ListModel, newItem: ListModel): Boolean {
        return oldItem == newItem
    }
}