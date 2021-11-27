package com.lomovskiy.pagedbsd.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lomovskiy.pagedbsd.navigation.Coordinator

class UuidsPagedBsdViewModel(
    private val coordinator: Coordinator
) : ViewModel() {

    private val state = MutableLiveData(UuidPagedBsd.State(null, null))

    fun handleAction(action: Action) {
        when (action) {
            Action.Close -> {
                coordinator.finish()
            }
            is Action.SelectedListItem -> {
                state.value = state.value!!.copy(selectedUuid = action.item)
                coordinator.replaceOn(Pages.PageThird)
            }
            is Action.PressedButtonNumber -> {
                state.value = state.value!!.copy(selectedPosition = action.number)
                coordinator.replaceOn(Pages.PageSecond)
            }
            Action.PressedButtonBackToFirst -> {
                state.value = state.value!!.copy(selectedPosition = null)
                coordinator.backTo(Pages.PageFirst)
            }
            Action.PressedButtonBackToSecond -> {
                state.value = state.value!!.copy(selectedPosition = null)
                coordinator.backTo(Pages.PageSecond)
            }
        }
    }

    fun getStateStream(): LiveData<UuidPagedBsd.State> {
        return state
    }

    sealed class Action {
        class PressedButtonNumber(val number: Int) : Action()
        class SelectedListItem(val item: CharSequence) : Action()
        object PressedButtonBackToFirst : Action()
        object PressedButtonBackToSecond : Action()
        object Close : Action()
    }

}
