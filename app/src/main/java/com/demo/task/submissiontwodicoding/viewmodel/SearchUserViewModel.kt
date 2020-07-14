package com.demo.task.submissiontwodicoding.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.demo.task.submissiontwodicoding.models.Item
import com.demo.task.submissiontwodicoding.models.SearchUser
import com.demo.task.submissiontwodicoding.network.isNetworkAvailable
import com.demo.task.submissiontwodicoding.repository.GithubRepository
import com.demo.task.submissiontwodicoding.utils.LoadingStatus
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.net.UnknownHostException

class SearchUserViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob: Job = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val applicationModel = application
    private val repository = GithubRepository(applicationModel)

    private val _searchData = MutableLiveData<SearchUser>()
    val searchData: LiveData<SearchUser>
        get() = _searchData

    private val _loadingStatus = MutableLiveData<LoadingStatus>()
    val loadingStatus: LiveData<LoadingStatus>
        get() = _loadingStatus

    /** Click Method for RecyclerView **/
    private val _selectedUser = MutableLiveData<Item>()
    val selectedUser: LiveData<Item>
        get() = _selectedUser

    fun onListUserClicked(item: Item) {
        _selectedUser.value = item
    }

    fun onDoneClicked() {
        _selectedUser.value = null
    }

    /** HandlerException for Coroutine **/
    private val handler = CoroutineExceptionHandler { _, exception ->
        if (exception is HttpException) {
            Log.d("ExceptionHttp", exception.message.toString())
        } else if (exception is UnknownHostException) {
            Log.d("ExceptionUnknownHost", exception.message.toString())
        }
        _loadingStatus.value = LoadingStatus.ERROR
    }

    fun searchUserDefault(username: String) {
        if (isNetworkAvailable(applicationModel.applicationContext)) {
            _loadingStatus.value = LoadingStatus.LOADING

            viewModelScope.launch(handler) {
                repository.getSearchedUsername(username).apply {
                    _searchData.value = this
                }
                _loadingStatus.value = LoadingStatus.SUCCESS
            }
        } else {
            _loadingStatus.value = LoadingStatus.NO_CONNECTION
        }
    }

    /** Canceling Job if Fragment is Destroyed **/
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchUserViewModel::class.java)) return SearchUserViewModel(application) as T

            throw IllegalArgumentException("Unable to Construct the ViewModel")
        }
    }
}