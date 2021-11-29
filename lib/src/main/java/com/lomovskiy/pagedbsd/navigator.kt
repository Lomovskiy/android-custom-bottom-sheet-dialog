package com.lomovskiy.pagedbsd

import androidx.fragment.app.*

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

    protected open fun setupFragmentTransaction(
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {}

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
        val fragmentTransaction: FragmentTransaction = dialogFragment.childFragmentManager.beginTransaction()
        val fragmentFactory: FragmentFactory = dialogFragment.childFragmentManager.fragmentFactory
        val classLoader: ClassLoader = dialogFragment.requireContext().classLoader
        val currentFragment: Fragment? = dialogFragment.childFragmentManager.findFragmentById(containerResId)
        val nextFragment: Fragment = fragmentFactory.instantiate(classLoader, page.classRef.name)
        fragmentTransaction.setReorderingAllowed(true)
        setupFragmentTransaction(fragmentTransaction, currentFragment, nextFragment)
        fragmentTransaction.replace(containerResId, nextFragment, page.key)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(page.key)
            localStack.add(page.key)
        }
        fragmentTransaction.commit()
    }

    private fun copyStack() {
        localStack = ArrayList()
        for (i in 0 until dialogFragment.childFragmentManager.backStackEntryCount) {
            localStack.add(dialogFragment.childFragmentManager.getBackStackEntryAt(i).name!!)
        }
    }

}
