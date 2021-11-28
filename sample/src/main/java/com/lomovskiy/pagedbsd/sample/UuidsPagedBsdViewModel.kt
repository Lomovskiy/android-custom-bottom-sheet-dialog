package com.lomovskiy.pagedbsd.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lomovskiy.pagedbsd.*

class UuidsPagedBsdViewModel(
    private val navigator: PagedBsdNavigator
) : ViewModel() {

    private val state = MutableLiveData(UuidPagedBsd.State(null, null))

    fun handleAction(action: Action) {
        when (action) {
            Action.Start -> {
                navigator.executeCommand(Replace(Pages.First))
            }
            Action.Close -> {
                navigator.executeCommands(arrayOf(
                    BackToRoot,
                    Back
                ))
            }
            is Action.SelectedListItem -> {
                state.value = state.value!!.copy(selectedUuid = action.item)
                navigator.executeCommand(Forward(Pages.Third))
            }
            is Action.PressedButtonNumber -> {
                state.value = state.value!!.copy(selectedPosition = action.number)
                navigator.executeCommand(Forward(Pages.Second))
            }
            Action.PressedButtonBackToFirst -> {
                state.value = state.value!!.copy(selectedPosition = null)
                navigator.executeCommand(Back)
            }
            Action.PressedButtonBackToSecond -> {
                navigator.executeCommand(Back)
            }
        }
    }

    fun getStateStream(): LiveData<UuidPagedBsd.State> {
        return state
    }

    sealed class Action {
        object Start : Action()
        class PressedButtonNumber(val number: Int) : Action()
        class SelectedListItem(val item: CharSequence) : Action()
        object PressedButtonBackToFirst : Action()
        object PressedButtonBackToSecond : Action()
        object Close : Action()
    }

}
