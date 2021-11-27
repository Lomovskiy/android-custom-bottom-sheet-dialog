package com.lomovskiy.pagedbsd

import androidx.lifecycle.LiveData

interface PagedBsdViewModel {

    abstract fun handleAction(action: Action)

    abstract fun getStateStream(): LiveData<PagedBsd.State>

    interface Action

}
