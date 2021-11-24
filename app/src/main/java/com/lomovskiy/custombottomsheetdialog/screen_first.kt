package com.lomovskiy.custombottomsheetdialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ScreenFirst : Fragment(R.layout.screen_first), View.OnClickListener {

    private val vm: ScreenFirstVM by viewModels(
        factoryProducer = {
            requireParentFragment() as ViewModelProvider.Factory
        }
    )

    private lateinit var buttonGoToNext: Button

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {

        override fun handleOnBackPressed() {
            vm.handleAction(ScreenFirstVM.Action.OnBackPressed)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
        buttonGoToNext = view.findViewById(R.id.button_go_to_next)
        buttonGoToNext.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        vm.handleAction(ScreenFirstVM.Action.OnButtonClicked(5))
    }

}

class ScreenFirstVM(
    private val coordinator: Coordinator
) : ViewModel() {

    fun handleAction(action: Action) {
        when (action) {
            Action.OnBackPressed -> {
                coordinator.handleCommand(Coordinator.Command.Finish)
            }
            is Action.OnButtonClicked -> {
                when (action.number) {
                    else -> coordinator.handleCommand(Coordinator.Command.OpenSecondStep)
                }
            }
        }
    }

    sealed class Action {
        object OnBackPressed : Action()
        class OnButtonClicked(val number: Int) : Action()
    }

}
