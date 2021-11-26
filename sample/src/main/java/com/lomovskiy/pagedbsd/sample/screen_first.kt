package com.lomovskiy.pagedbsd.sample

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider

abstract class PageBase(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected val vm: PagedBottomSheetDialogFragmentVM by viewModels(
        factoryProducer = { requireParentFragment() as ViewModelProvider.Factory },
        ownerProducer = { requireParentFragment() }
    )

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {

        override fun handleOnBackPressed() {
            onBackPressed()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    abstract fun onBackPressed()

}

class PageFirst : PageBase(R.layout.page_first), View.OnClickListener {

    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button

    override fun onBackPressed() {
        vm.handleAction(PagedBottomSheetDialogFragmentVM.Action.Close)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button1 = view.findViewById(R.id.button_1)
        button2 = view.findViewById(R.id.button_2)
        button3 = view.findViewById(R.id.button_3)
        button4 = view.findViewById(R.id.button_4)
        button5 = view.findViewById(R.id.button_5)
        button6 = view.findViewById(R.id.button_6)
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)
        button6.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        vm.handleAction(
            PagedBottomSheetDialogFragmentVM.Action.OnNumberButtonPressed(
                getNumberById(
                    v.id
                )
            )
        )
    }

    private fun getNumberById(id: Int): Int {
        return when (id) {
            R.id.button_1 -> 1
            R.id.button_2 -> 2
            R.id.button_3 -> 3
            R.id.button_4 -> 4
            R.id.button_5 -> 5
            R.id.button_6 -> 6
            else -> throw IllegalStateException()
        }
    }

}
