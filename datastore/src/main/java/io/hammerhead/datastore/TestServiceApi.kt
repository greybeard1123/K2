package io.hammerhead.datastore

import io.hammerhead.aidl.TestAIDL

object TestServiceApi : TestAIDL.Stub() {
    override fun sendMessage(message: String?) {
        println("******* sendMessage in service: $message")
    }

    override fun getMessage(): String {
        val message = "HAHAAHAH"
        println("***** Got message in service: $message")
        return message
    }
}