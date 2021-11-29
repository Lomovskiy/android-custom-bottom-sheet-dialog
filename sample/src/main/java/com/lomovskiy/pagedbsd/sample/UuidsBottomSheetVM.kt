package com.lomovskiy.pagedbsd.sample

import com.lomovskiy.pagedbsd.*
import java.util.*

class UuidsBottomSheetVM(
    private val coordinator: Coordinator
) : PagedBottomSheetVM<UuidsBottomSheetVM.Action, UuidsBottomSheet.State>(
    UuidsBottomSheet.State.empty()
) {

    override fun handleAction(action: Action) {
        when (action) {
            Action.Start -> {
                coordinator.start()
            }
            Action.Close -> {
                coordinator.finish()
            }
            is Action.SelectedListItem -> {
                state.value = state.value.copy(selectedUuid = action.item)
                coordinator.onUuidSelected()
            }
            is Action.PressedButtonNumber -> {
                val uuids: List<CharSequence> = List(action.number) {
                    UUID.randomUUID().toString()
                }
                state.value = state.value.copy(uuids = uuids)
                coordinator.onNumberSelected()
            }
            Action.PressedButtonBackToFirst -> {
                state.value = state.value.copy(uuids = null)
                coordinator.onBackToSelectNumber()
            }
            Action.PressedButtonBackToSecond -> {
                coordinator.onBackToSelectUuid()
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
