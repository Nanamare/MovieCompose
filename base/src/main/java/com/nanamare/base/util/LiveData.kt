package com.nanamare.base.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> LiveData<T>.set(value: T) {
    (this as? MutableLiveData)?.value = value
}