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
            is BackTo -> handleBackTo(command.route)
            is Forward -> handleForwardTo(command.route)
            is Replace -> handleReplaceOn(command.route)
        }
    }

    private fun handleBackTo(route: Route?) {
        if (route == null) {
            resetStack()
        } else {
            val key: String = route.key
            val idx: Int = localStack.indexOf(key)
            if (idx == -1) {
                resetStack()
            } else {
                val forRemove = localStack.subList(idx, localStack.size)
                dialogFragment.childFragmentManager.popBackStack(forRemove.first().toString(), 0)
                forRemove.clear()
            }
        }
    }

    private fun handleBack() {
        if (localStack.isNotEmpty()) {
            dialogFragment.childFragmentManager.popBackStack()
            localStack.removeLast()
        } else {
            dialogFragment.dismiss()
        }
    }

    private fun handleForwardTo(route: Route) {
        commitPage(route, true)
    }

    private fun handleReplaceOn(route: Route) {
        if (localStack.isNotEmpty()) {
            dialogFragment.childFragmentManager.popBackStack()
            localStack.removeLast()
            commitPage(route, true)
        } else {
            commitPage(route, false)
        }
    }

    private fun commitPage(route: Route, addToBackStack: Boolean) {
        val fragmentTransaction: FragmentTransaction = dialogFragment.childFragmentManager.beginTransaction()
        val fragmentFactory: FragmentFactory = dialogFragment.childFragmentManager.fragmentFactory
        val classLoader: ClassLoader = dialogFragment.requireContext().classLoader
        val currentFragment: Fragment? = dialogFragment.childFragmentManager.findFragmentById(containerResId)
        val nextFragment: Fragment = fragmentFactory.instantiate(classLoader, route.classRef.name)
        fragmentTransaction.setReorderingAllowed(true)
        setupFragmentTransaction(fragmentTransaction, currentFragment, nextFragment)
        fragmentTransaction.replace(containerResId, nextFragment, route.key)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(route.key)
            localStack.add(route.key)
        }
        fragmentTransaction.commit()
    }

    private fun resetStack() {
        localStack = ArrayList()
        dialogFragment.childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun copyStack() {
        localStack = ArrayList()
        for (i in 0 until dialogFragment.childFragmentManager.backStackEntryCount) {
            localStack.add(dialogFragment.childFragmentManager.getBackStackEntryAt(i).name!!)
        }
    }

}
