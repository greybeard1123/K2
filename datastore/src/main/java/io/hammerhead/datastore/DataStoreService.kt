package io.hammerhead.datastore

import android.app.Service
import android.content.Intent
import android.os.IBinder

class DataStoreService : Service() {
    override fun onCreate() {
        super.onCreate()
        println("****** Service created")
    }
    
    override fun onBind(intent: Intent?): IBinder {
        println("******* onBind ${intent?.component?.className}")
        return TestServiceApi
    }

    override fun onUnbind(intent: Intent?): Boolean {
        println("****** onUnbind ${intent?.component?.className}")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        println("******* Service destroyed")
    }
}