package com.lomovskiy.pagedbsd.sample

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lomovskiy.pagedbsd.PagedBsdViewModel
import java.lang.IllegalStateException

class ScreenFactory : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        when (className) {
            PageFirst::class.java.name -> {
                return PageFirst()
            }
            PageSecond::class.java.name -> {
                return PageSecond()
            }
            PageThird::class.java.name -> {
                return PageThird()
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

}

class UuidsPagedBsdVM(
    private val navigator: Navigator<SelectUUIDNavCommand>,
    private val deps1: String,
    private val deps2: String
) : PagedBsdViewModel<UuidsPagedBsdVM.Action>() {

    private val state = MutableLiveData(PagedBottomSheetDialogFragment.State.empty())

    override fun handleAction(action: Action) {
        when (action) {
            Action.Close -> {
                navigator.handleCommand(SelectUUIDNavCommand.Finish)
            }
            Action.OnListItemButtonPressed -> {
                navigator.handleCommand(SelectUUIDNavCommand.Finish)
            }
            is Action.OnListItemClicked -> {
                state.value = state.value!!.copy(selectedString = action.string)
                navigator.handleCommand(SelectUUIDNavCommand.OpenThirdStep)
            }
            is Action.OnNumberButtonPressed -> {
                state.value = state.value!!.copy(selectedNumber = action.number)
                navigator.handleCommand(SelectUUIDNavCommand.OpenSecondStep)
            }
            Action.OnBackToFirstStepPressed -> {
                state.value = state.value!!.copy(selectedNumber = null)
                navigator.handleCommand(SelectUUIDNavCommand.OpenFirstStep)
            }
            Action.OnBackToSecondStepPressed -> {
                state.value = state.value!!.copy(selectedString = null)
                navigator.handleCommand(SelectUUIDNavCommand.OpenSecondStep)
            }
        }
    }

    sealed class Action {
        class OnNumberButtonPressed(val number: Int) : Action()
        class OnListItemClicked(val string: CharSequence) : Action()
        object OnListItemButtonPressed : Action()
        object OnBackToFirstStepPressed : Action()
        object OnBackToSecondStepPressed : Action()
        object Close : Action()
    }

}
