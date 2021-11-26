package com.lomovskiy.custombottomsheetdialog

import androidx.fragment.app.DialogFragment

sealed class State {
    object Finished : State()
    object StepOne : State()
    object StepTwo : State()
    object StepThree : State()
}

interface Navigator<> {
    fun handleState(state: State)
}

class NavigatorImpl(
    private val dialogFragment: DialogFragment
) : Navigator {

    override fun handleState(state: State) {
        when (state) {
            State.Finished -> {
                dialogFragment.dismissAllowingStateLoss()
            }
            State.StepOne -> {
                dialogFragment.childFragmentManager.beginTransaction()
                    .replace(R.id.container, PageFirst::class.java, null)
                    .commit()
            }
            State.StepTwo -> {
                dialogFragment.childFragmentManager.beginTransaction()
                    .replace(R.id.container, PageSecond::class.java, null)
                    .commit()
            }
            is State.StepThree -> {
                dialogFragment.childFragmentManager.beginTransaction()
                    .replace(R.id.container, PageThird::class.java, null)
                    .commit()
            }
        }
    }

}
