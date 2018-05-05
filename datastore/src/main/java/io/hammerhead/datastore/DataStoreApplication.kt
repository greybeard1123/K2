package io.hammerhead.datastore

import io.hammerhead.commons.logging.BaseApplication

class DataStoreApplication : BaseApplication() {

    override fun isDebug(): Boolean {
        return when (BuildConfig.BUILD_TYPE) {
            "debug" -> true
            else -> false
        }
    }
}