package com.lomovskiy.pagedbsd.sample

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

internal inline fun Fragment.getDepsProvider(): DepsProvider {
    when {
        parentFragment is DepsProvider -> {
            return parentFragment as DepsProvider
        }
        activity is DepsProvider -> {
            return activity as DepsProvider
        }
        else -> {
            throw IllegalStateException("parent fragment or activity must implement DepsProvider interface")
        }
    }
}

inline fun FragmentActivity.showDialog(tag: String, dialog: DialogFragment?) {
    if (dialog == null) {
        return
    }
    if (isFinishing) {
        return
    }
    if (supportFragmentManager.findFragmentByTag(tag) != null) {
        return
    }
    dialog.show(supportFragmentManager, tag)
}
