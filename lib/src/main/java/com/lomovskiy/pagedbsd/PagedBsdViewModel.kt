package com.lomovskiy.pagedbsd

import androidx.lifecycle.ViewModel

abstract class PagedBsdViewModel<A> : ViewModel() {

    abstract fun handleAction(action: A)

}
