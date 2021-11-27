package com.lomovskiy.pagedbsd.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

interface Page {
    val key: String
    val clazz: Class<out Fragment>
}
