package com.lomovskiy.pagedbsd.sample.pages.third

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.lomovskiy.pagedbsd.PagedBsdPage
import com.lomovskiy.pagedbsd.sample.R
import com.lomovskiy.pagedbsd.sample.UuidsPagedBsdVM

class PageThird : PagedBsdPage<UuidsPagedBsdVM, UuidsPagedBsdVM.State, UuidsPagedBsdVM.Action>(R.layout.page_third),
    View.OnClickListener {

    override val key: String = PageThird::class.java.simpleName
    override val clazz: Class<out Fragment> = PageThird::class.java

    private lateinit var buttonStub: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getStateStream().observe(viewLifecycleOwner, {
            buttonStub.text = it.selectedUuid
        })
        buttonStub = view.findViewById(R.id.button_stub)
        buttonStub.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        vm.handleAction(UuidsPagedBsdVM.Action.OnListItemButtonPressed)
    }

    override fun onBackPressed() {
        vm.handleAction(UuidsPagedBsdVM.Action.OnBackToSecondStepPressed)
    }

}
