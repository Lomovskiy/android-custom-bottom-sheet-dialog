package com.lomovskiy.pagedbsd

import androidx.fragment.app.Fragment

interface Route {
    val key: String
    val classRef: Class<out Fragment>
}
