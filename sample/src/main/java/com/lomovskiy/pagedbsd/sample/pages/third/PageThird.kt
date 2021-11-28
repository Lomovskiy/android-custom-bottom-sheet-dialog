package com.lomovskiy.pagedbsd.sample.pages.third

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lomovskiy.pagedbsd.sample.R
import com.lomovskiy.pagedbsd.sample.UuidsPagedBsd
import com.lomovskiy.pagedbsd.sample.UuidsPagedBsdVM
import com.lomovskiy.pagedbsd.PagedBsdPage

class PageThird : PagedBsdPage<UuidsPagedBsdVM.Action, UuidsPagedBsd.State, UuidsPagedBsdVM>(
    R.layout.page_third,
    UuidsPagedBsdVM::class
), View.OnClickListener {

    private lateinit var buttonStub: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonStub = view.findViewById(R.id.button_stub)
        buttonStub.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        vm.handleAction(UuidsPagedBsdVM.Action.Close)
    }

    override fun onBackPressed() {
        vm.handleAction(UuidsPagedBsdVM.Action.PressedButtonBackToSecond)
    }

    override fun renderState(state: UuidsPagedBsd.State) {
        buttonStub.text = state.selectedUuid
    }

}
