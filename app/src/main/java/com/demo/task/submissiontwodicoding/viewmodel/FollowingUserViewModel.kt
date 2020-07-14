package com.demo.task.submissiontwodicoding.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.demo.task.submissiontwodicoding.models.FollowingUser
import com.demo.task.submissiontwodicoding.network.isNetworkAvailable
import com.demo.task.submissiontwodicoding.repository.GithubRepository
import com.demo.task.submissiontwodicoding.utils.LoadingStatus
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.UnknownHostException

class FollowingUserViewModel(application: Application, username: String?) :
    AndroidViewModel(application) {

    companion object {
        private val TAG = FollowingUserViewModel::class.java.simpleName
    }

    private val viewModelJob: Job = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val applicationModel = application
    private val repository = GithubRepository(applicationModel)

    private val _followingUser = MutableLiveData<List<FollowingUser>>()
    val followingUser: LiveData<List<FollowingUser>>
        get() = _followingUser

    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    /** HandlerException for Coroutine **/
    private val handler = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is HttpException -> Log.d(TAG, "ExceptionHttp ${exception.message.toString()}")
            is UnknownHostException -> Log.d(
                TAG,
                "ExceptionUnknownHost ${exception.message.toString()}"
            )
            else -> Log.d(TAG, exception.message.toString())
        }
        _loadingStatus.value = LoadingStatus.ERROR
    }

    init {
        username?.let { getFollowingData(it) }
    }

    fun getFollowingData(username: String) {
        if (isNetworkAvailable(applicationModel.applicationContext)) {
            _loadingStatus.value = LoadingStatus.LOADING

            viewModelScope.launch(handler) {
                repository.getFollowing(username).apply {
                    _followingUser.value = this
                }
                _loadingStatus.value = LoadingStatus.SUCCESS
            }
        } else {
            _loadingStatus.value = LoadingStatus.NO_CONNECTION
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(private val application: Application, private val username: String) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FollowingUserViewModel::class.java))
                return FollowingUserViewModel(application, username) as T
            throw IllegalArgumentException("Unable to Construct the ViewModel")
        }
    }
}