package com.nanamare.test_shared

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class MovieTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        clazzLoader: ClassLoader?,
        name: String?,
        context: Context?
    ): Application =
        super.newApplication(clazzLoader, HiltTestApplication::class.java.name, context)

}