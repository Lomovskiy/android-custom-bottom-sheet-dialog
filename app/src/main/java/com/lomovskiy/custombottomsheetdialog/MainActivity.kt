package com.lomovskiy.custombottomsheetdialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttonOpen: Button
    private lateinit var buttonClose: Button

    private var customBottomSheetDialogFragment: CustomBottomSheetDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonOpen = findViewById(R.id.button_open)
        buttonClose = findViewById(R.id.button_close)
        buttonOpen.setOnClickListener {
            customBottomSheetDialogFragment?.show(supportFragmentManager, "TAG")
        }
        buttonClose.setOnClickListener {
            customBottomSheetDialogFragment?.dismiss()
        }
        customBottomSheetDialogFragment = CustomBottomSheetDialogFragment()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_open -> {
                customBottomSheetDialogFragment?.show(supportFragmentManager, "TAG")
            }
            R.id.button_close -> {
                customBottomSheetDialogFragment?.dismiss()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}

class CustomBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var container: FrameLayout

    private lateinit var dialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_custom_bottom_sheet, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = object : BottomSheetDialog(requireContext(), theme) {

            override fun onBackPressed() {
                if (childFragmentManager.backStackEntryCount == 0) {
                    super.onBackPressed()
                    return
                }
                childFragmentManager.popBackStack()
            }

        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        container = view.findViewById(R.id.container)
        childFragmentManager.beginTransaction()
            .replace(R.id.container, ScreenSecond())
            .addToBackStack(ScreenFirst::class.simpleName)
            .commit()
    }



    fun onGoToSecond() {
        childFragmentManager.beginTransaction()
            .replace(R.id.container, ScreenFirst())
            .addToBackStack(ScreenSecond::class.simpleName)
            .commit()
    }

    fun onGoToThird() {
        childFragmentManager.beginTransaction()
            .replace(R.id.container, ScreenThird())
            .addToBackStack(ScreenSecond::class.simpleName)
            .commit()
    }

}