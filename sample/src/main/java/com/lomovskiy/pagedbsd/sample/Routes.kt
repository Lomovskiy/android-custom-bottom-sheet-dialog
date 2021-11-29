package com.lomovskiy.pagedbsd.sample

import androidx.fragment.app.Fragment
import com.lomovskiy.pagedbsd.Route
import com.lomovskiy.pagedbsd.sample.pages.first.PageFirst
import com.lomovskiy.pagedbsd.sample.pages.second.PageSecond
import com.lomovskiy.pagedbsd.sample.pages.third.PageThird

object Routes {

    object First : Route {

        override val key: String = PageFirst::class.java.name

        override val classRef: Class<out Fragment> = PageFirst::class.java

    }

    object Second : Route {

        override val key: String = PageSecond::class.java.name

        override val classRef: Class<out Fragment> = PageSecond::class.java

    }

    object Third : Route {

        override val key: String = PageThird::class.java.name

        override val classRef: Class<out Fragment> = PageThird::class.java

    }

}
