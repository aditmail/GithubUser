package com.demo.task.submissiontwodicoding.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.demo.task.submissiontwodicoding.R
import com.demo.task.submissiontwodicoding.adapter.UserFollowersAdapter
import com.demo.task.submissiontwodicoding.adapter.UserFollowingAdapter
import com.demo.task.submissiontwodicoding.adapter.UserSearchAdapter
import com.demo.task.submissiontwodicoding.models.FollowersUser
import com.demo.task.submissiontwodicoding.models.FollowingUser
import com.demo.task.submissiontwodicoding.models.Item

/** TextView Binding Adapter for Loading Screen Status **/
@BindingAdapter("textViewConnectionStatus")
fun TextView.connectionStatus(status: LoadingStatus?) {
    val imgDrawable: Drawable
    status?.let {
        when (status) {
            LoadingStatus.SUCCESS,
            LoadingStatus.LOADING -> {
                visibility = View.GONE
            }
            LoadingStatus.ERROR -> {
                visibility = View.VISIBLE
                imgDrawable = ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.ic_undraw_no_data_xml,
                    null
                ) as Drawable
                setCompoundDrawablesRelativeWithIntrinsicBounds(null, imgDrawable, null, null)

                text = context.resources.getString(R.string.error_status)
            }
            LoadingStatus.NO_CONNECTION -> {
                visibility = View.VISIBLE
                imgDrawable = ResourcesCompat.getDrawable(
                    context.resources,
                    R.drawable.ic_undraw_internet_xml,
                    null
                ) as Drawable
                setCompoundDrawablesRelativeWithIntrinsicBounds(null, imgDrawable, null, null)

                text = context.resources.getString(R.string.no_internet)
            }
        }
    }
}

@BindingAdapter("textViewSearchResult")
fun TextView.searchResult(total_count: Int?) {
    val imgDrawable: Drawable
    total_count?.let {
        if (it != 0) {
            text = context.resources.getQuantityString(R.plurals.resultFound, it, it)
            setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
        } else {
            imgDrawable = ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.ic_undraw_no_data_xml,
                null
            ) as Drawable
            setCompoundDrawablesRelativeWithIntrinsicBounds(null, imgDrawable, null, null)
            text = context.resources.getString(R.string.result_data_not_found)
        }
    }
}

@BindingAdapter("textViewCountData")
fun TextView.countData(total_count: Int?) {
    val imgDrawable: Drawable
    total_count?.let {
        if (it != 0) {
            visibility = View.GONE
        } else {
            visibility = View.VISIBLE
            imgDrawable = ResourcesCompat.getDrawable(
                context.resources,
                R.drawable.ic_undraw_no_data_xml,
                null
            ) as Drawable
            setCompoundDrawablesRelativeWithIntrinsicBounds(null, imgDrawable, null, null)
            text = context.resources.getString(R.string.no_data)
        }
    }
}

/** Progress Bar Binding Adapter for Loading Screen **/
@BindingAdapter("loadingBarConnectionStatus")
fun ProgressBar.connectionStatus(status: LoadingStatus?) {
    status?.let {
        visibility = when (status) {
            LoadingStatus.LOADING -> View.VISIBLE
            else -> View.GONE
        }
    }
}

/** Loading Screen Visibility Binding Adapter **/
@BindingAdapter("setLoadingLayout")
fun setLoadingLayout(view: View, status: LoadingStatus?) {
    status?.let {
        when (status) {
            LoadingStatus.SUCCESS -> view.visibility = View.GONE
            else -> view.visibility = View.VISIBLE
        }
    }
}

/** Check if RecyclerView contain data or not **/
@BindingAdapter("recyclerConnectionStatus")
fun RecyclerView.connectionStatus(status: LoadingStatus?) {
    status?.let {
        visibility = when (status) {
            LoadingStatus.SUCCESS -> View.VISIBLE
            else -> View.GONE
        }
    }
}

/** And Check if RecyclerView contain data or not **/
@BindingAdapter("recyclerDataCount")
fun dataCount(view: View, totalCount: Int?) {
    totalCount?.let {
        if (it == 0) {
            view.visibility = View.GONE
        } else {
            view.visibility = View.VISIBLE
        }
    }
}

/** RecyclerView Inserting Data For Search User **/
@BindingAdapter("listUserData")
fun listUserData(recyclerView: RecyclerView, data: List<Item>?) {
    data?.let {
        val adapter = recyclerView.adapter as UserSearchAdapter
        adapter.submitList(it)
    }
}

/** RecyclerView Inserting Data For Followers User **/
@BindingAdapter("listUserFollowers")
fun listUserFollowers(recyclerView: RecyclerView, data: List<FollowersUser>?) {
    data?.let {
        val adapter = recyclerView.adapter as UserFollowersAdapter
        adapter.submitList(it)
    }
}

/** RecyclerView Inserting Data For Following User **/
@BindingAdapter("listUserFollowing")
fun listUserFollowing(recyclerView: RecyclerView, data: List<FollowingUser>?) {
    data?.let {
        val adapter = recyclerView.adapter as UserFollowingAdapter
        adapter.submitList(it)
    }
}

/** Image Binding **/
@BindingAdapter("userAvatar")
fun userAvatar(imageView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
            .dontAnimate()
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_anim)
                    .error(R.drawable.logo_drawable)
            )
            .into(imageView)
    }
}

@BindingAdapter("setTextData")
fun TextView.setTextData(data: String?) {
    if (data == null) {
        visibility = View.GONE
    } else {
        text = data
        visibility = View.VISIBLE
    }
}
