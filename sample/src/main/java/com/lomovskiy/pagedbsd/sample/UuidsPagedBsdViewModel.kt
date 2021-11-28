package com.lomovskiy.pagedbsd.sample

import com.lomovskiy.pagedbsd.*

class UuidsPagedBsdViewModel(
    private val navigator: Navigator
) : BaseViewModel<UuidsPagedBsdViewModel.Action, UuidPagedBsd.State>(
    UuidPagedBsd.State.empty()
) {

    override fun handleAction(action: Action) {
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

    sealed class Action {
        object Start : Action()
        class PressedButtonNumber(val number: Int) : Action()
        class SelectedListItem(val item: CharSequence) : Action()
        object PressedButtonBackToFirst : Action()
        object PressedButtonBackToSecond : Action()
        object Close : Action()
    }

}
