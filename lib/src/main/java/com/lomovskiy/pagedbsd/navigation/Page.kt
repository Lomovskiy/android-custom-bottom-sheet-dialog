package com.lomovskiy.pagedbsd.navigation

import androidx.fragment.app.Fragment

interface Page {
    val key: String
    val clazz: Class<out Fragment>
}
