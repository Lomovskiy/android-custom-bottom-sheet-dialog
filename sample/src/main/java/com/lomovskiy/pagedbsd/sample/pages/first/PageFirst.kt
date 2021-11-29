package com.lomovskiy.pagedbsd.sample.pages.first

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.lomovskiy.pagedbsd.sample.R
import com.lomovskiy.pagedbsd.sample.UuidsBottomSheet
import com.lomovskiy.pagedbsd.sample.UuidsBottomSheetVM
import com.lomovskiy.pagedbsd.PagedBottomSheetPage

class PageFirst : PagedBottomSheetPage<UuidsBottomSheetVM.Action, UuidsBottomSheet.State, UuidsBottomSheetVM>(
    R.layout.page_first,
    UuidsBottomSheetVM::class
), View.OnClickListener {

    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button

    override fun onBackPressed() {
        vm.handleAction(UuidsBottomSheetVM.Action.Close)
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
        vm.handleAction(UuidsBottomSheetVM.Action.PressedButtonNumber(getNumberById(v.id)))
    }

    override fun renderState(state: UuidsBottomSheet.State) {}

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
