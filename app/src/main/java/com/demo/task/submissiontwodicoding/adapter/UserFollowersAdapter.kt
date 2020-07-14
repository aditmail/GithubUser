package com.demo.task.submissiontwodicoding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.task.submissiontwodicoding.R
import com.demo.task.submissiontwodicoding.adapter.UserFollowersAdapter.UserFollowersViewHolder
import com.demo.task.submissiontwodicoding.databinding.UserListFollowersItemBinding
import com.demo.task.submissiontwodicoding.models.FollowersUser

class UserFollowersAdapter :
    ListAdapter<FollowersUser, UserFollowersViewHolder>(DiffCallback) {

    /** Checking if There's New/Fresh Data In RecyclerView **/
    companion object DiffCallback : DiffUtil.ItemCallback<FollowersUser>() {
        override fun areItemsTheSame(
            old: FollowersUser,
            new: FollowersUser
        ): Boolean {
            return old == new
        }

        override fun areContentsTheSame(
            old: FollowersUser,
            new: FollowersUser
        ): Boolean {
            return old.login == new.login
        }
    }

    class UserFollowersViewHolder(private val binding: UserListFollowersItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(followersUser: FollowersUser) {
            binding.itemFollowers = followersUser
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFollowersViewHolder {
        val binding: UserListFollowersItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.user_list_followers_item,
            parent, false
        )

        return UserFollowersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserFollowersViewHolder, position: Int) {
        val itemFollowers = getItem(position)
        holder.bind(itemFollowers)
    }

}
