package com.lomovskiy.pagedbsd.navigation

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

interface Navigator {

    fun executeCommands(commands: Array<out PagedBsdNavigationCommand>)

}

open class PagedBsdNavigator(
    dialog: DialogFragment,
    private val containerResId: Int
) : Navigator {

    private val childFragmentManager: FragmentManager = dialog.childFragmentManager
    private val parentFragmentManager: FragmentManager = dialog.parentFragmentManager

    private var fragmentManagerStackCopy = ArrayList<String>()

    override fun executeCommands(commands: Array<out PagedBsdNavigationCommand>) {
        childFragmentManager.executePendingTransactions()
        fragmentManagerStackCopy = ArrayList()
        for (i in 0 until childFragmentManager.backStackEntryCount) {
            fragmentManagerStackCopy.add(childFragmentManager.getBackStackEntryAt(i).name!!)
        }
        commands.forEach {
            when (it) {
                PagedBsdNavigationCommand.Back -> handleBack()
                is PagedBsdNavigationCommand.BackTo -> handleBackTo(it.page)
                is PagedBsdNavigationCommand.ForwardTo -> handleForwardTo(it.page)
                is PagedBsdNavigationCommand.ReplaceOn -> handleReplaceOn(it.page)
            }
        }
    }

    protected open fun setupFragmentTransaction(
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {}

    private fun handleBack() {
        if (fragmentManagerStackCopy.isNotEmpty()) {
            childFragmentManager.popBackStack()
            fragmentManagerStackCopy.removeAt(fragmentManagerStackCopy.lastIndex)
        } else {
            parentFragmentManager.popBackStack()
        }
    }

    private fun handleBackTo(page: Page?) {
        if (page == null) {
            backToRoot()
            return
        }
        val pageKey: String = page.key
        val pageIdx: Int = fragmentManagerStackCopy.indexOf(pageKey)
        if (pageIdx == -1) {
            backToRoot()
            return
        }
        val forRemove = fragmentManagerStackCopy.subList(pageIdx, fragmentManagerStackCopy.size)
        childFragmentManager.popBackStack(forRemove.first().toString(), 0)
        forRemove.clear()
    }

    private fun handleForwardTo(page: Page) {
        commitPage(page, true)
    }

    private fun handleReplaceOn(page: Page) {
        if (fragmentManagerStackCopy.isNotEmpty()) {
            childFragmentManager.popBackStack()
            fragmentManagerStackCopy.removeAt(fragmentManagerStackCopy.lastIndex)
            commitPage(page, true)
        } else {
            commitPage(page, false)
        }
    }

    private fun commitPage(page: Page, addToBackStack: Boolean) {
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        val currentFragment: Fragment? = childFragmentManager.findFragmentById(containerResId)
        val nextFragment: Class<out Fragment> = page.clazz
        transaction.setReorderingAllowed(true)
        setupFragmentTransaction(transaction, currentFragment, nextFragment.newInstance())
        transaction.replace(containerResId, nextFragment, null, page.key)
        if (addToBackStack) {
            transaction.addToBackStack(page.key)
            fragmentManagerStackCopy.add(page.key)
        }
        transaction.commit()
    }

    private fun backToRoot() {
        fragmentManagerStackCopy = ArrayList()
        childFragmentManager.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

}
