package com.lomovskiy.pagedbsd.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lomovskiy.pagedbsd.PagedBsdViewModel
import com.lomovskiy.pagedbsd.navigation.PagedBsdCoordinator

class UuidsPagedBsdViewModel(
    private val coordinator: PagedBsdCoordinator
) : PagedBsdViewModel() {

    private val state = MutableLiveData(State.empty())

    override fun handleAction(action: PagedBsdViewModel.Action) {
        when (action) {
            Action.Close -> {
                coordinator.finish()
            }
            Action.OnListItemButtonPressed -> {
                coordinator.finish()
            }
            is Action.OnListItemClicked -> {
                state.value = state.value!!.copy(selectedUuid = action.string)
                coordinator.replaceOn(Pages.PageThird)
            }
            is Action.OnNumberButtonPressed -> {
                state.value = state.value!!.copy(selectedPosition = action.number)
                coordinator.replaceOn(Pages.PageSecond)
            }
            Action.OnBackToFirstStepPressed -> {
                state.value = state.value!!.copy(selectedPosition = null)
                coordinator.backTo(Pages.PageFirst)
            }
            Action.OnBackToSecondStepPressed -> {
                state.value = state.value!!.copy(selectedPosition = null)
                coordinator.backTo(Pages.PageSecond)
            }
        }
    }

    override fun getStateStream(): LiveData<State> {
        return state
    }

    sealed class Action : PagedBsdViewModel.Action {
        class OnNumberButtonPressed(val number: Int) : Action()
        class OnListItemClicked(val string: CharSequence) : Action()
        object OnListItemButtonPressed : Action()
        object OnBackToFirstStepPressed : Action()
        object OnBackToSecondStepPressed : Action()
        object Close : Action()
    }

}
