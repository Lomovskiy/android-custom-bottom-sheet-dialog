package com.lomovskiy.pagedbsd

import androidx.fragment.app.Fragment

interface Page {
    val key: String
    val classRef: Class<out Fragment>
}
