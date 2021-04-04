package com.ijniclohot.ticketnativechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ijniclohot.ticketnativechallenge.model.GithubUser
import com.ijniclohot.ticketnativechallenge.model.Resource
import com.ijniclohot.ticketnativechallenge.networking.NetworkingInstance
import io.reactivex.disposables.CompositeDisposable

class MainViewModel : ViewModel() {
    private val githubUserLiveData = MutableLiveData<Resource<List<GithubUser>>>()
    private val disposable = CompositeDisposable()
    private var page = 0
    private val networkingInstance = NetworkingInstance.apiService

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    private fun getGithubUserLiveData(): LiveData<Resource<List<GithubUser>>> {
        return githubUserLiveData
    }

    fun retrieveUsers(username: String) {
        
    }

}