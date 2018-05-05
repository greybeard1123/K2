package io.hammerhead.commons.logging

import android.app.Application
import timber.log.Timber

abstract class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupLogging()
    }

    private fun setupLogging() {
        Timber.plant(getTimberTree())
    }

    private fun getTimberTree(): Timber.Tree {
        return if (isDebug()) {
            println("****** Planting debug tree")
            Timber.DebugTree()
        } else {
            LoggingTree()
        }
    }

    abstract fun isDebug(): Boolean
}