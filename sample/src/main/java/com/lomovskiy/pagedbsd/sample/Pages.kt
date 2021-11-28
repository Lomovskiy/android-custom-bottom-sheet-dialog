package com.lomovskiy.pagedbsd.sample

import androidx.fragment.app.Fragment
import com.lomovskiy.pagedbsd.Page
import com.lomovskiy.pagedbsd.sample.pages.first.PageFirst
import com.lomovskiy.pagedbsd.sample.pages.second.PageSecond
import com.lomovskiy.pagedbsd.sample.pages.third.PageThird

object Pages {

    object First : Page {

        override val key: String = PageFirst::class.java.name

        override val classRef: Class<out Fragment> = PageFirst::class.java

    }

    object Second : Page {

        override val key: String = PageSecond::class.java.name

        override val classRef: Class<out Fragment> = PageSecond::class.java

    }

    object Third : Page {

        override val key: String = PageThird::class.java.name

        override val classRef: Class<out Fragment> = PageThird::class.java

    }

}
