package com.lomovskiy.pagedbsd.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

interface Navigator {

    fun executeCommand(command: NavigationCommand)

    fun executeCommands(commands: Array<out NavigationCommand>)

}

open class PagedBsdNavigator(
    private val containerResId: Int,
    private val childFragmentManager: FragmentManager,
    private val parentFragmentManager: FragmentManager
) : Navigator {

    private var fragmentManagerStackCopy = ArrayList<String>()

    override fun executeCommand(command: NavigationCommand) {
        childFragmentManager.executePendingTransactions()
        fragmentManagerStackCopy = ArrayList()
        for (i in 0 until childFragmentManager.backStackEntryCount) {
            fragmentManagerStackCopy.add(childFragmentManager.getBackStackEntryAt(i).name!!)
        }
        when (command) {
            Back -> handleBack()
            is BackTo -> handleBackTo(command.page)
            is ForwardTo -> handleForwardTo(command.page)
            is ReplaceOn -> handleReplaceOn(command.page)
            else -> {}
        }
    }

    override fun executeCommands(commands: Array<out NavigationCommand>) {
        childFragmentManager.executePendingTransactions()
        fragmentManagerStackCopy = ArrayList()
        for (i in 0 until childFragmentManager.backStackEntryCount) {
            fragmentManagerStackCopy.add(childFragmentManager.getBackStackEntryAt(i).name!!)
        }
        commands.forEach {
            when (it) {
                Back -> handleBack()
                is BackTo -> handleBackTo(it.page)
                is ForwardTo -> handleForwardTo(it.page)
                is ReplaceOn -> handleReplaceOn(it.page)
                else -> {}
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
