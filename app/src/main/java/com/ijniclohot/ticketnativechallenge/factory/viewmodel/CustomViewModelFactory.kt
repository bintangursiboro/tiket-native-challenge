package com.ijniclohot.ticketnativechallenge.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ijniclohot.ticketnativechallenge.viewmodel.MainViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel() as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}