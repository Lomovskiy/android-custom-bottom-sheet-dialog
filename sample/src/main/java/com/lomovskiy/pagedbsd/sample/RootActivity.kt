package com.lomovskiy.pagedbsd.sample

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RootActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttonOpen: Button
    private lateinit var buttonClose: Button

    private var uuidsPagedBsd: UuidsPagedBsd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        buttonOpen = findViewById(R.id.button_open)
        buttonClose = findViewById(R.id.button_close)
        buttonOpen.setOnClickListener(this)
        buttonClose.setOnClickListener(this)
        uuidsPagedBsd = UuidsPagedBsd()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_open -> {
                uuidsPagedBsd!!.show(supportFragmentManager, "tag")
            }
            R.id.button_close -> {}
        }
    }

}
