package com.ijniclohot.ticketnativechallenge.base_view

interface BaseListViewInterface<T> {
    fun onSuccessView(dataList: List<T>)

    fun onErrorView(errorMsg: String)

    fun onLoadMoreView()

    fun onErrorLoadMore(errorMsg: String)
}