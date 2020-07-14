package com.demo.task.submissiontwodicoding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.task.submissiontwodicoding.R
import com.demo.task.submissiontwodicoding.adapter.UserFollowingAdapter.UserFollowingViewHolder
import com.demo.task.submissiontwodicoding.databinding.UserListFollowingItemBinding
import com.demo.task.submissiontwodicoding.models.FollowingUser

class UserFollowingAdapter :
    ListAdapter<FollowingUser, UserFollowingViewHolder>(DiffCallback) {

    /** Checking if There's New/Fresh Data In RecyclerView **/
    companion object DiffCallback : DiffUtil.ItemCallback<FollowingUser>() {
        override fun areItemsTheSame(
            old: FollowingUser,
            new: FollowingUser
        ): Boolean {
            return old == new
        }

        override fun areContentsTheSame(
            old: FollowingUser,
            new: FollowingUser
        ): Boolean {
            return old.login == new.login
        }
    }

    class UserFollowingViewHolder(private val binding: UserListFollowingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(followingUser: FollowingUser) {
            binding.itemFollowing = followingUser
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFollowingViewHolder {
        val binding: UserListFollowingItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.user_list_following_item,
            parent, false
        )

        return UserFollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserFollowingViewHolder, position: Int) {
        val itemFollowing = getItem(position)
        holder.bind(itemFollowing)
    }
}