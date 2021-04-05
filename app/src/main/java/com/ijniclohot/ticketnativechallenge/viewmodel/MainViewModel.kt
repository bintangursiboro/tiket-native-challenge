package com.ijniclohot.ticketnativechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ijniclohot.ticketnativechallenge.model.GithubUser
import com.ijniclohot.ticketnativechallenge.model.Resource
import com.ijniclohot.ticketnativechallenge.networking.NetworkingInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val githubUserLiveData = MutableLiveData<Resource<List<GithubUser>>>()
    private val disposable = CompositeDisposable()
    private var page = 1
    private lateinit var username: String
    private val networkingInstance = NetworkingInstance.apiService

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun getGithubUserLiveData(): LiveData<Resource<List<GithubUser>>> {
        return githubUserLiveData
    }

    fun retrieveUsers(username: String) {
        this.username = username

        page = 1

        githubUserLiveData.postValue(Resource.loading(null))

        disposable.add(
            networkingInstance.getUsers(username, page, 15).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ onSuccess ->

                    if (onSuccess.items.isEmpty()) {
                        githubUserLiveData.postValue(Resource.error("Username not found", null))
                    } else {
                        githubUserLiveData.postValue(Resource.success(onSuccess.items))
                        page++
                    }

                }, { onError ->
                    githubUserLiveData.postValue(Resource.error("Something wrong happened.", null))
                    onError.printStackTrace()
                })
        )


    }

    fun loadMoreData() {
        githubUserLiveData.postValue(Resource.loadMore(githubUserLiveData.value!!.data))

        disposable.add(
            networkingInstance.getUsers(username, page, 15).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ onSuccess ->
                    val currentValue = githubUserLiveData.value!!.data as ArrayList
                    currentValue.addAll(onSuccess.items)
                    githubUserLiveData.postValue(Resource.success(currentValue))
                    page++
                }, { onError ->
                    githubUserLiveData.postValue(
                        Resource.loadMoreError(
                            githubUserLiveData.value!!.data,
                            "Something wrong happened."
                        )
                    )
                    onError.printStackTrace()
                })
        )
    }

}