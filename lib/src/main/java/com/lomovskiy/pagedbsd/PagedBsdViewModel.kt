package com.lomovskiy.pagedbsd

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class PagedBsdViewModel<S, A> : ViewModel() where S : PagedBsdState, A : PagedBsdViewModelAction {

    abstract fun handleAction(action: A)

    abstract fun getStateStream(): LiveData<S>

}
