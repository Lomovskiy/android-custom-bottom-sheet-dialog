package com.lomovskiy.pagedbsd.sample

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.viewModels
import com.lomovskiy.pagedbsd.PagedBsd

class RootActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttonOpen: Button
    private lateinit var buttonClose: Button

    private var uuidPagedBsd: UuidsPagedBsd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        buttonOpen = findViewById(R.id.button_open)
        buttonClose = findViewById(R.id.button_close)
        buttonOpen.setOnClickListener(this)
        buttonClose.setOnClickListener(this)
        uuidPagedBsd = UuidsPagedBsd()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_open -> {
                uuidPagedBsd!!.show(supportFragmentManager, "tag")
            }
            R.id.button_close -> {}
        }
    }

}

class UuidsPagedBsd : PagedBsd<UuidsPagedBsdVM, UuidsPagedBsdVM.State, UuidsPagedBsdVM.Action>() {

    override val viewModel: UuidsPagedBsdVM by viewModels()
    override val pageFactory: FragmentFactory = PageFactory()

}
