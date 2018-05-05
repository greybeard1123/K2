package io.hammerhead.commons.serviceConnection

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.IBinder
import io.hammerhead.commons.ServiceException
import io.hammerhead.commons.logging.Logger.serviceConnectionLog
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import timber.log.Timber.*

open class RxServiceConnection(private val context: Context,
                          private val serviceIntent: Intent) : ServiceConnection, ObservableOnSubscribe<IBinder> {

    private lateinit var emitter: ObservableEmitter<IBinder>

    override fun subscribe(emitter: ObservableEmitter<IBinder>) {
        this.emitter = emitter
        bind()
    }

    private fun bind() {
        if (!::emitter.isInitialized) return
        try {
            d(serviceConnectionLog("Binding to service ${serviceIntent.component.shortClassName}"))
            context.bindService(serviceIntent, this, BIND_AUTO_CREATE)
        } catch (exception: Exception) {
            e(exception, serviceConnectionLog("Could not bind to service ${serviceIntent.component.shortClassName}"))
            if (!isPackageInstalled()) {
                emitter.onError(ServiceException.NotInstalledException)
            } else {
                emitter.onError(ServiceException.BindException)
            }
        }
    }

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
        i(serviceConnectionLog("Connected to service ${name.shortClassName}"))
        if (!::emitter.isInitialized) return
        if (emitter.isDisposed) return

        emitter.onNext(service)
    }

    override fun onServiceDisconnected(name: ComponentName) {
        i(serviceConnectionLog("Disconnected from service ${name.shortClassName}"))
        if (!::emitter.isInitialized) return
        if (emitter.isDisposed) return

        i(serviceConnectionLog("Rebinding to service ${name.shortClassName}"))
        bind()
    }

    private fun isPackageInstalled(): Boolean {
        val packageName = serviceIntent.component.packageName
        return try {
            context.packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            e(serviceConnectionLog("Service $packageName not installed"))
            false
        }
    }
}