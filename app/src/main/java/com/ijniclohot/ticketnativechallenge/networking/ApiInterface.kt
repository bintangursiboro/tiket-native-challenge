package com.ijniclohot.ticketnativechallenge.networking

import com.ijniclohot.ticketnativechallenge.model.GithubUsersResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("search/users")
    fun getUsers(@Query(value = "q") userName: String, @Query(value = "page")page:Int, @Query(value = "per_page") perPage:Int) : Observable<GithubUsersResponse>
}