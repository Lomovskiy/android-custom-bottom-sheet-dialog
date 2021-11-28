package com.lomovskiy.pagedbsd

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

interface Navigator {

    fun executeCommand(command: NavigationCommand)

    fun executeCommands(commands: Array<out NavigationCommand>)

}

open class PagedBsdNavigator(
    private val containerResId: Int,
    private val dialogFragment: DialogFragment
) : Navigator {

    private var localStack = ArrayList<String>()

    override fun executeCommand(command: NavigationCommand) {
        dialogFragment.childFragmentManager.executePendingTransactions()
        copyStack()
        executeCommandInternal(command)
    }

    override fun executeCommands(commands: Array<out NavigationCommand>) {
        dialogFragment.childFragmentManager.executePendingTransactions()
        copyStack()
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
        localStack = ArrayList()
        dialogFragment.childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun handleBack() {
        if (localStack.isNotEmpty()) {
            dialogFragment.childFragmentManager.popBackStack()
            localStack.removeLast()
        } else {
            dialogFragment.dismiss()
        }
    }

    private fun handleForwardTo(page: Page) {
        commitPage(page, true)
    }

    private fun handleReplaceOn(page: Page) {
        if (localStack.isNotEmpty()) {
            dialogFragment.childFragmentManager.popBackStack()
            localStack.removeLast()
            commitPage(page, true)
        } else {
            commitPage(page, false)
        }
    }

    private fun commitPage(page: Page, addToBackStack: Boolean) {
        val transaction: FragmentTransaction = dialogFragment.childFragmentManager.beginTransaction()
//        val currentFragment: Fragment? = childFragmentManager.findFragmentById(containerResId)
        val nextFragment: Class<out Fragment> = page.classRef
        transaction.setReorderingAllowed(true)
//        setupFragmentTransaction(transaction, currentFragment, nextFragment.newInstance())
        transaction.replace(containerResId, nextFragment, null, page.key)
        if (addToBackStack) {
            transaction.addToBackStack(page.key)
            localStack.add(page.key)
        }
        transaction.commit()
    }

    private fun copyStack() {
        localStack = ArrayList()
        for (i in 0 until dialogFragment.childFragmentManager.backStackEntryCount) {
            localStack.add(dialogFragment.childFragmentManager.getBackStackEntryAt(i).name!!)
        }
    }

}
