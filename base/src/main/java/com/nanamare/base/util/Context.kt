package com.nanamare.base.util

import android.content.Context
import android.widget.Toast

var toast: Toast? = null

fun Context.toast(content: String) {
    toast?.cancel()
    toast = Toast.makeText(this, content, Toast.LENGTH_SHORT)
    toast?.show()
}