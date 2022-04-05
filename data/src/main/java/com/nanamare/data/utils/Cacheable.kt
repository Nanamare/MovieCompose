package com.nanamare.data.utils

import java.util.concurrent.TimeUnit

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Cacheable(val value: Int, val timeUnit: TimeUnit)