package com.demo.task.submissiontwodicoding.repository

import android.content.Context
import android.util.Log
import com.demo.task.submissiontwodicoding.models.DetailUser
import com.demo.task.submissiontwodicoding.models.FollowersUser
import com.demo.task.submissiontwodicoding.models.FollowingUser
import com.demo.task.submissiontwodicoding.models.SearchUser
import com.demo.task.submissiontwodicoding.network.RetrofitServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class GithubRepository(context: Context) {

    companion object {
        private val TAG = GithubRepository::class.java.simpleName
    }

    private var _context: Context = context

    /** Repo for Get Searched User **/
    suspend fun getSearchedUsername(username: String): SearchUser? {
        var getSearchData: SearchUser? = null
        withContext(Dispatchers.IO) {
            getSearchData = try {
                RetrofitServices(_context).getServiceAPI().searchUser(username).await()
            } catch (e: HttpException) {
                Log.d(TAG, e.message().toString())
                null
            }
        }

        return getSearchData
    }

    /** Repo for Get Detail User **/
    suspend fun getDetailUsername(username: String): DetailUser? {
        var getDetailData: DetailUser? = null
        withContext(Dispatchers.IO) {
            getDetailData = try {
                RetrofitServices(_context).getServiceAPI().detailUser(username).await()
            } catch (e: HttpException) {
                Log.d(TAG, e.message().toString())
                null
            }
        }

        return getDetailData
    }

    /** Repo for Get Followers List User **/
    suspend fun getFollowers(username: String): List<FollowersUser>? {
        var getFollowerData: List<FollowersUser>? = null
        withContext(Dispatchers.IO) {
            getFollowerData = try {
                RetrofitServices(_context).getServiceAPI().followerUser(username).await()
            } catch (e: HttpException) {
                Log.d(TAG, e.message().toString())
                null
            }
        }

        return getFollowerData
    }

    /** Repo for Get Following List User **/
    suspend fun getFollowing(username: String): List<FollowingUser>? {
        var getFollowingData: List<FollowingUser>? = null
        withContext(Dispatchers.IO) {
            getFollowingData = try {
                RetrofitServices(_context).getServiceAPI().followingUser(username).await()
            } catch (e: HttpException) {
                Log.d(TAG, e.message().toString())
                null
            }
        }

        return getFollowingData
    }
}
