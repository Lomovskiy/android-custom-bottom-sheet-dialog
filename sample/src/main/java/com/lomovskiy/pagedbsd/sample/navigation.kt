package com.lomovskiy.pagedbsd.sample

import androidx.fragment.app.DialogFragment
import com.lomovskiy.pagedbsd.sample.pages.first.PageFirst
import com.lomovskiy.pagedbsd.sample.pages.second.PageSecond
import com.lomovskiy.pagedbsd.sample.pages.third.PageThird

sealed class SelectUUIDNavCommand {
    object OpenFirstStep : SelectUUIDNavCommand()
    object OpenSecondStep : SelectUUIDNavCommand()
    object OpenThirdStep : SelectUUIDNavCommand()
    object Finish : SelectUUIDNavCommand()
}

interface Navigator<T> {
    fun handleCommand(command: T)
}

class NavigatorImpl(
    private val dialogFragment: DialogFragment
) : Navigator<SelectUUIDNavCommand> {

    override fun handleCommand(command: SelectUUIDNavCommand) {
        when (command) {
            SelectUUIDNavCommand.Finish -> {
                dialogFragment.dismissAllowingStateLoss()
            }
            SelectUUIDNavCommand.OpenFirstStep -> {
                dialogFragment.childFragmentManager.beginTransaction()
                    .replace(R.id.container, PageFirst::class.java, null)
                    .commit()
            }
            SelectUUIDNavCommand.OpenSecondStep -> {
                dialogFragment.childFragmentManager.beginTransaction()
                    .replace(R.id.container, PageSecond::class.java, null)
                    .commit()
            }
            SelectUUIDNavCommand.OpenThirdStep -> {
                dialogFragment.childFragmentManager.beginTransaction()
                    .replace(R.id.container, PageThird::class.java, null)
                    .commit()
            }
        }
    }

}
