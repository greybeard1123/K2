package io.hammerhead.datastore

import android.app.Service
import android.content.Intent
import android.os.IBinder
import io.hammerhead.commons.logging.Logger.dataStoreLog
import timber.log.Timber.d

class DataStoreService : Service() {

    override fun onBind(intent: Intent): IBinder {
        d(dataStoreLog("Binding to ${intent.component.shortClassName}"))
        return TestServiceApi()
    }

    override fun onUnbind(intent: Intent): Boolean {
        d(dataStoreLog("Unbinding from ${intent.component.shortClassName}"))
        return super.onUnbind(intent)
    }
}