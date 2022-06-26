package com.test.users_app.util

import androidx.test.espresso.idling.CountingIdlingResource

object ExpressoIdlingResource {
    private const val RESOURCE = "GLOBAL"
    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}