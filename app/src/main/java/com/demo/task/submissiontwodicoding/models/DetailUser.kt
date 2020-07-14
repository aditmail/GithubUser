package com.demo.task.submissiontwodicoding.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailUser(
    val avatar_url: String?,
    val bio: Any?,
    val blog: String?,
    val company: String?,
    val created_at: String?,
    val email: Any?,
    val events_url: String?,
    val followers: Double?,
    val followers_url: String?,
    val following: Double?,
    val following_url: String?,
    val gists_url: String?,
    val gravatar_id: String?,
    val hireable: Any?,
    val html_url: String?,
    val id: Int?,
    val location: String?,
    val login: String?,
    val name: String?,
    val node_id: String?,
    val organizations_url: String?,
    val public_gists: Int?,
    val public_repos: Double?,
    val received_events_url: String?,
    val repos_url: String?,
    val site_admin: Boolean?,
    val starred_url: String?,
    val subscriptions_url: String?,
    val twitter_username: Any?,
    val type: String?,
    val updated_at: String?,
    val url: String?
)