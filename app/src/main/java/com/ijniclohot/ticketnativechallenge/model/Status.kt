package com.ijniclohot.ticketnativechallenge.model

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> loadMore(data: T?): Resource<T> {
            return Resource(Status.LOAD_MORE, data, null)
        }

        fun <T> loadMoreError(data: T?, msg: String): Resource<T> {
            return Resource(Status.LOAD_MORE_ERROR, data, msg)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    LOAD_MORE,
    LOAD_MORE_ERROR,
}