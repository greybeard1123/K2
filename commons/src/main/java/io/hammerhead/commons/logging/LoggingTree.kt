package io.hammerhead.commons.logging

import android.util.Log
import timber.log.Timber

class LoggingTree: Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) return

        //Logging controller
    }
}