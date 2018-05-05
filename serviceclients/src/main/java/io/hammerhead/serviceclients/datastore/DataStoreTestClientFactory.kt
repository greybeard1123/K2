package io.hammerhead.serviceclients.datastore

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.IBinder
import io.hammerhead.commons.serviceConnection.BaseServiceClientFactory

class DataStoreTestClientFactory(context: Context) : BaseServiceClientFactory<DataStoreTestClient>(context) {

    override fun isBackgroundService(): Boolean = false

    override fun getServiceIntent(): Intent {
        val intent = Intent()
        intent.component = ComponentName(PACKAGE_NAME, CLASS_NAME)
        return intent
    }

    override fun client(binder: IBinder): DataStoreTestClient = DataStoreTestClient(binder)

    companion object {
        private const val PACKAGE_NAME = "io.hammerhead.datastore"
        private const val CLASS_NAME = "io.hammerhead.datastore.DataStoreService"
    }
}