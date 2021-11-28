package com.lomovskiy.pagedbsd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<Action, State> : ViewModel() {
    
    protected val state = MutableLiveData<State>()
    
    fun getStateStream(): LiveData<State> {
        return state
    }
    
    abstract fun handleAction(action: Action)
    
}