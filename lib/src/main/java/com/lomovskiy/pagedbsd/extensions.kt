package com.lomovskiy.pagedbsd


import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

internal fun Fragment.findViewModelProvider(): ViewModelProvider.Factory {
    if (parentFragment != null) {
        if (parentFragment is ViewModelProvider.Factory) {
            return parentFragment as ViewModelProvider.Factory
        }
        return parentFragment!!.findViewModelProvider()
    } else {
        if (activity is ViewModelProvider.Factory) {
            return activity as ViewModelProvider.Factory
        } else {
            throw IllegalStateException("Couldn't find viewModel provider")
        }
    }
}

internal inline fun Fragment.findViewModelStore(): ViewModelStoreOwner {
    if (parentFragment != null) {
        if (parentFragment is ViewModelStoreOwner) {
            return parentFragment as ViewModelProvider.Factory
        }
        return parentFragment!!.findViewModelProvider()
    } else {
        if (activity is ViewModelProvider.Factory) {
            return activity as ViewModelProvider.Factory
        } else {
            throw IllegalStateException("Couldn't find viewModel provider")
        }
    }
}