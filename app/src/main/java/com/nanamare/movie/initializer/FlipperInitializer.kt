package com.nanamare.movie.initializer

import android.content.Context
import android.os.Build
import androidx.startup.Initializer
import com.nanamare.base.debug.Flipper

class FlipperInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            // https://github.com/facebook/flipper/issues/3572
            Flipper.init(context)
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}