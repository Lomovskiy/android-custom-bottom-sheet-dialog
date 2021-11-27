package com.lomovskiy.pagedbsd

import androidx.fragment.app.Fragment

internal fun <VM> Fragment.findPagedBsdViewModelProvider(): PagedBsdViewModelProvider<VM> {
    if (parentFragment != null) {
        if (parentFragment is PagedBsdViewModelProvider<*>) {
            return parentFragment as PagedBsdViewModelProvider<VM>
        }
        return parentFragment!!.findPagedBsdViewModelProvider()
    } else {
        if (activity is PagedBsdViewModelProvider<*>) {
            return activity as PagedBsdViewModelProvider<VM>
        } else {
            throw IllegalStateException("Couldn't find view model provider")
        }
    }
}
