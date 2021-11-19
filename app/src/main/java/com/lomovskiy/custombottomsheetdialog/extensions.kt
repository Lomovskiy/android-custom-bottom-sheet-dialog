package com.lomovskiy.custombottomsheetdialog

import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

interface NavigationMessage

interface NavigationRouter<T : NavigationMessage> {

    fun handleNavigationMessage(message: T)

}

inline fun <T : NavigationMessage> Fragment.sendNavigationMessage(message: T) {
    when {
        parentFragment is NavigationRouter<*> -> {
            (parentFragment as NavigationRouter<T>).handleNavigationMessage(message)
        }
        activity is NavigationRouter<*> -> {
            (activity as NavigationRouter<T>).handleNavigationMessage(message)
        }

        else -> {
            throw IllegalStateException("Your activity can't handle navigation message")
        }
    }
}

inline fun Fragment.openScreen(@IdRes containerViewId: Int,
                               screen: Class<out Fragment>,
                               tag: String,
                               addToBackStack: Boolean) {
    childFragmentManager.beginTransaction().apply {
        replace(containerViewId, screen, null, tag)
        if (addToBackStack) {
            addToBackStack(tag)
        }
    }.commit()

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
