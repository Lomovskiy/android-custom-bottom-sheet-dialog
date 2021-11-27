package com.lomovskiy.pagedbsd

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lomovskiy.pagedbsd.navigation.Coordinator
import com.lomovskiy.pagedbsd.navigation.PagedBsdNavigator

abstract class PagedBsdViewModel<S, A>(
    private val coordinator: Coordinator
) : ViewModel() where S : PagedBsdState, A : PagedBsdViewModelAction {

    abstract fun handleAction(action: A)

    abstract fun getStateStream(): LiveData<S>

}
