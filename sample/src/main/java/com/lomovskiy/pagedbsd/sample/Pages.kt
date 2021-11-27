package com.lomovskiy.pagedbsd.sample

import androidx.fragment.app.Fragment
import com.lomovskiy.pagedbsd.navigation.Page

object Pages {

    object PageFirst : Page {

        override val key: String = PageFirst::class.java.name

        override val clazz: Class<out Fragment> = com.lomovskiy.pagedbsd.sample.pages.first.PageFirst::class.java

    }

    object PageSecond : Page {

        override val key: String = PageSecond::class.java.name

        override val clazz: Class<out Fragment> = com.lomovskiy.pagedbsd.sample.pages.second.PageSecond::class.java

    }

    object PageThird : Page {

        override val key: String = PageThird::class.java.name

        override val clazz: Class<out Fragment> = com.lomovskiy.pagedbsd.sample.pages.third.PageThird::class.java

    }

}
