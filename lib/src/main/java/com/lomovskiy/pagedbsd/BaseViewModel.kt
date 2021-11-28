package com.lomovskiy.pagedbsd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<A, S>(initialState: S) : ViewModel() {
    
    protected val state = MutableLiveData<S>()

    init {
        state.value = initialState
    }
    
    fun getStateStream(): LiveData<S> {
        return state
    }
    
    abstract fun handleAction(action: A)
    
}