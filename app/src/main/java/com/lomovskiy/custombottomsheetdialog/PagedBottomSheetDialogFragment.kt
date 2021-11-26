package com.lomovskiy.custombottomsheetdialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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

class PagedBottomSheetDialogFragment : BottomSheetDialogFragment(), ViewModelProvider.Factory {

    private val navigator: Navigator = NavigatorImpl(this)
    private val coordinator: Coordinator = CoordinatorImpl(navigator)

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = ScreenFactory()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : BottomSheetDialog(requireContext(), theme) {

            override fun onBackPressed() {
                requireActivity().onBackPressed()
            }

        }.apply {
            setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomDialogStyle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_custom_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        coordinator.handleCommand(Coordinator.Command.OpenFirstStep)
    }

    companion object {

        const val TAG = "CustomBottomSheetDialogFragment"

        fun newInstance(): PagedBottomSheetDialogFragment {
            return PagedBottomSheetDialogFragment()
        }

    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when (modelClass) {
            PagedBottomSheetDialogFragmentVM::class.java -> {
                return PagedBottomSheetDialogFragmentVM(coordinator) as T
            }
            else -> {
                return modelClass.newInstance()
            }
        }
    }

    data class State(
        val selectedString: CharSequence?,
        val selectedNumber: Int?
    ) {

        companion object {

            fun empty(): State {
                return State(
                    selectedString = null,
                    selectedNumber = null
                )
            }

        }

    }



}

class PagedBottomSheetDialogFragmentVM(
    private val coordinator: Coordinator
) : ViewModel() {

    private val state = MutableLiveData(PagedBottomSheetDialogFragment.State.empty())

    fun getState(): LiveData<PagedBottomSheetDialogFragment.State> {
        return state
    }

    fun setState(state: PagedBottomSheetDialogFragment.State) {
        this.state.value = state
    }

    fun handleAction(action: Action) {
        when (action) {
            Action.Close -> {
                coordinator.handleCommand(Coordinator.Command.Finish)
            }
            Action.OnListItemButtonPressed -> {
                coordinator.handleCommand(Coordinator.Command.Finish)
            }
            is Action.OnListItemClicked -> {
                state.value = state.value!!.copy(selectedString = action.string)
                coordinator.handleCommand(Coordinator.Command.OpenThirdStep)
            }
            is Action.OnNumberButtonPressed -> {
                state.value = state.value!!.copy(selectedNumber = action.number)
                coordinator.handleCommand(Coordinator.Command.OpenSecondStep)
            }
            Action.OnBackToFirstStepPressed -> {
                state.value = state.value!!.copy(selectedNumber = null)
                coordinator.handleCommand(Coordinator.Command.OpenFirstStep)
            }
            Action.OnBackToSecondStepPressed -> {
                state.value = state.value!!.copy(selectedString = null)
                coordinator.handleCommand(Coordinator.Command.OpenSecondStep)
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
