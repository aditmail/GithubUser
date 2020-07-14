package com.demo.task.submissiontwodicoding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.task.submissiontwodicoding.R
import com.demo.task.submissiontwodicoding.adapter.UserSearchAdapter.UserSearchViewHolder
import com.demo.task.submissiontwodicoding.databinding.UserListItemBinding
import com.demo.task.submissiontwodicoding.models.Item

class UserSearchAdapter(private val clickListener: UserSearchListener) :
    ListAdapter<Item, UserSearchViewHolder>(DiffCallback) {

    /** Checking if There's New/Fresh Data In RecyclerView **/
    companion object DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.login == newItem.login
        }
    }

    class UserSearchViewHolder(private val binding: UserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: UserSearchListener, item: Item) {
            binding.itemUser = item
            binding.executePendingBindings()

            binding.clickListener = clickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserSearchViewHolder {
        val binding: UserListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.user_list_item,
            parent, false
        )
        return UserSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserSearchViewHolder, position: Int) {
        val itemUser = getItem(position)
        holder.bind(clickListener, itemUser)
    }
}

/** Click Listener for RecyclerView to Get Specific Data **/
class UserSearchListener(val clickListener: (item: Item) -> Unit) {
    fun onClickListener(item: Item) = clickListener(item)
}