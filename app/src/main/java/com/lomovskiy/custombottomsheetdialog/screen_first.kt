package com.lomovskiy.custombottomsheetdialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment

class ScreenFirst : Fragment(R.layout.screen_first), View.OnClickListener {

    private lateinit var buttonGoToNext: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonGoToNext = view.findViewById(R.id.button_go_to_next)
        buttonGoToNext.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        (requireParentFragment() as CustomBottomSheetDialogFragment).onGoToThird()
    }

}
