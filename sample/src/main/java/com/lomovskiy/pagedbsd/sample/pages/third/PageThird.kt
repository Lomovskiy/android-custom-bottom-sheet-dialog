package com.lomovskiy.pagedbsd.sample.pages.third

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lomovskiy.pagedbsd.sample.R
import com.lomovskiy.pagedbsd.sample.UuidPagedBsd
import com.lomovskiy.pagedbsd.sample.UuidsPagedBsdViewModel
import com.lomovskiy.pagedbsd.PagedBsdPage

class PageThird : PagedBsdPage<UuidsPagedBsdViewModel.Action, UuidPagedBsd.State, UuidsPagedBsdViewModel>(R.layout.page_third),
    View.OnClickListener {

    override val vm: UuidsPagedBsdViewModel by viewModels(
        ownerProducer = { parentFragment as ViewModelStoreOwner },
        factoryProducer = { parentFragment as ViewModelProvider.Factory }
    )

    private lateinit var buttonStub: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonStub = view.findViewById(R.id.button_stub)
        buttonStub.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        vm.handleAction(UuidsPagedBsdViewModel.Action.Close)
    }

    override fun onBackPressed() {
        vm.handleAction(UuidsPagedBsdViewModel.Action.PressedButtonBackToSecond)
    }

    override fun renderState(state: UuidPagedBsd.State) {
        buttonStub.text = state.selectedUuid
    }

}
