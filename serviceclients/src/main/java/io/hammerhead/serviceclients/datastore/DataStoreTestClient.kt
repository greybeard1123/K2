package io.hammerhead.serviceclients.datastore

import android.os.IBinder
import io.hammerhead.aidl.TestAIDL

class DataStoreTestClient(private val binder: IBinder): TestAIDL.Stub() {

    override fun sendMessage(message: String) {
        serviceApi().sendMessage(message)
    }

    override fun getMessage(): String {
        return serviceApi().message
    }

    private fun serviceApi(): TestAIDL {
        return TestAIDL.Stub.asInterface(binder)
    }
}