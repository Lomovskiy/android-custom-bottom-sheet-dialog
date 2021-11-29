package com.lomovskiy.pagedbsd

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

abstract class PagedBottomSheetVM<A, S>(initialState: S) : ViewModel() {
    
    protected val state = MutableStateFlow(initialState)
    
    fun getStateStream(): Flow<S> {
        return state
    }
    
    abstract fun handleAction(action: A)
    
}
