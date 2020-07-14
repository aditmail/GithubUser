package com.demo.task.submissiontwodicoding.network

import com.demo.task.submissiontwodicoding.models.DetailUser
import com.demo.task.submissiontwodicoding.models.FollowersUser
import com.demo.task.submissiontwodicoding.models.FollowingUser
import com.demo.task.submissiontwodicoding.models.SearchUser
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPIService {

    /** Get Query Search for User **/
    @GET("search/users")
    fun searchUser(@Query("q") username: String): Deferred<SearchUser>

    /** Get Detail User **/
    @GET("users/{username}")
    fun detailUser(@Path("username") username: String): Deferred<DetailUser>

    /** Get User Followers List **/
    @GET("users/{username}/followers")
    fun followerUser(@Path("username") username: String): Deferred<List<FollowersUser>>

    /** Get User Following List **/
    @GET("users/{username}/following")
    fun followingUser(@Path("username") username: String): Deferred<List<FollowingUser>>
}