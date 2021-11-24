package com.lomovskiy.custombottomsheetdialog

import android.os.Bundle
import androidx.fragment.app.DialogFragment

sealed class State {
    object Finished : State()
    object StepOne : State()
    object StepTwo : State()
    class StepThree(val item: CharSequence) : State()
}

interface Navigator {
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
                    .replace(R.id.container, ScreenFirst::class.java, null)
                    .commit()
            }
            State.StepTwo -> {
                dialogFragment.childFragmentManager.beginTransaction()
                    .replace(R.id.container, ScreenSecond::class.java, null)
                    .commit()
            }
            is State.StepThree -> {
                dialogFragment.childFragmentManager.beginTransaction()
                    .replace(
                        R.id.container, ScreenThird::class.java,
                        Bundle().apply {
                            putCharSequence("string", state.item)
                        }
                    )
                    .commit()
            }
        }
    }

}
