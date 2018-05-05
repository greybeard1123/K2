package io.hammerhead.commons.logging

object Logger {
    fun serviceConnectionLog(msg: String) = log(SERVICE_CONNECTION, msg)
    fun homeLog(msg: String) = log(HOME, msg)
    fun dataStoreLog(msg: String) = log(DATA_STORE, msg)


    private val log: (String, String) -> String = { TAG, message -> "$TAG: $message" }
}