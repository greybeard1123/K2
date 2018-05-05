package io.hammerhead.datastore

import io.hammerhead.aidl.TestAIDL
import io.hammerhead.commons.logging.Logger.dataStoreLog
import timber.log.Timber.d

class TestServiceApi : TestAIDL.Stub() {
    override fun sendMessage(message: String) {
        d(dataStoreLog("Service got message $message"))
    }

    override fun getMessage(): String {
        val message = "Test message from service"
        d(dataStoreLog("Service sending message $message"))
        return message
    }
}