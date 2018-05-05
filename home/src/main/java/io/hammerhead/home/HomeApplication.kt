package io.hammerhead.home

import io.hammerhead.commons.logging.BaseApplication

class HomeApplication: BaseApplication() {

    override fun isDebug(): Boolean {
        return when(BuildConfig.BUILD_TYPE) {
            "debug" -> true
            else -> false
        }
    }
}