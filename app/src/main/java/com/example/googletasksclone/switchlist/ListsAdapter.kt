package com.example.googletasksclone.switchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.googletasksclone.data.ListModel
import com.example.googletasksclone.databinding.ListItemLayoutBinding

class ListsAdapter : ListAdapter<ListModel, ListsAdapter.ViewHolder>(ListModelDiffCallback()) {
    var onListItemSelected: ((event: SwitchEvent) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onListItemSelected)
    }

    class ViewHolder private constructor(private val binding: ListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ListModel, onListItemSelected: ((event: SwitchEvent) -> Unit)?) {
            binding.root.setOnClickListener {
                onListItemSelected?.invoke(SwitchEvent.ItemSelected(item.id))
            }
            binding.title.text = item.title
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