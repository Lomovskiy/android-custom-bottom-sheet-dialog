package com.lomovskiy.pagedbsd.sample

import com.lomovskiy.pagedbsd.*
import java.util.*

class UuidsPagedBsdVM(
    private val navigator: Navigator
) : PagedBsdVM<UuidsPagedBsdVM.Action, UuidsPagedBsd.State>(
    UuidsPagedBsd.State.empty()
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
                val uuids: List<CharSequence> = List(action.number) {
                    UUID.randomUUID().toString()
                }
                state.value = state.value!!.copy(uuids = uuids)
                navigator.executeCommand(Forward(Pages.Second))
            }
            Action.PressedButtonBackToFirst -> {
                state.value = state.value!!.copy(uuids = null)
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
