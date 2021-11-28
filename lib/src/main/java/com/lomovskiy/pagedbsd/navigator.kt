package com.lomovskiy.pagedbsd

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.lomovskiy.pagedbsd.navigation.Page

interface PagedBsdNavigator {

    fun executeCommand(command: NavigationCommand)

    fun executeCommands(commands: Array<out NavigationCommand>)

}

open class PagedBsdNavigatorImpl(
    private val containerResId: Int,
    private val pagedBsd: DialogFragment
) : PagedBsdNavigator {

    override fun executeCommand(command: NavigationCommand) {
        pagedBsd.childFragmentManager.executePendingTransactions()
        executeCommandInternal(command)
    }

    override fun executeCommands(commands: Array<out NavigationCommand>) {
        pagedBsd.childFragmentManager.executePendingTransactions()
        commands.forEach(::executeCommandInternal)
    }

//    protected open fun setupFragmentTransaction(
//        fragmentTransaction: FragmentTransaction,
//        currentFragment: Fragment?,
//        nextFragment: Fragment
//    ) {}

    private fun executeCommandInternal(command: NavigationCommand) {
        when (command) {
            Back -> handleBack()
            BackToRoot -> handleBackToRoot()
            is Forward -> handleForwardTo(command.page)
            is Replace -> handleReplaceOn(command.page)
        }
    }

    private fun handleBackToRoot() {
        pagedBsd.childFragmentManager.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun handleBack() {
        if (pagedBsd.childFragmentManager.backStackEntryCount == 0) {
            pagedBsd.dismiss()
        } else {
            pagedBsd.childFragmentManager.popBackStack()
        }
    }

    private fun handleForwardTo(page: Page) {
        commitPage(page, true)
    }

    private fun handleReplaceOn(page: Page) {
        if (pagedBsd.childFragmentManager.backStackEntryCount != 0) {
            pagedBsd.childFragmentManager.popBackStack()
            commitPage(page, true)
        } else {
            commitPage(page, false)
        }
    }

    private fun commitPage(page: Page, addToBackStack: Boolean) {
        val transaction: FragmentTransaction = pagedBsd.childFragmentManager.beginTransaction()
//        val currentFragment: Fragment? = childFragmentManager.findFragmentById(containerResId)
        val nextFragment: Class<out Fragment> = page.classRef
        transaction.setReorderingAllowed(true)
//        setupFragmentTransaction(transaction, currentFragment, nextFragment.newInstance())
        transaction.replace(containerResId, nextFragment, null, page.key)
        if (addToBackStack) {
            transaction.addToBackStack(page.key)
        }
        transaction.commit()
    }

}
