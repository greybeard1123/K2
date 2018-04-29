package io.hammerhead.commons.serviceConnection

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.IBinder
import io.hammerhead.commons.ServiceException
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe

class RxServiceConnection(private val context: Context,
                          private val serviceIntent: Intent) : ServiceConnection, SingleOnSubscribe<IBinder> {

    private lateinit var emitter: SingleEmitter<IBinder>

    override fun subscribe(emitter: SingleEmitter<IBinder>) {
        println("****** Inside RxServiceConnection subscribe")
        this.emitter = emitter
        try {
            println("****** Binding to service $serviceIntent")
            context.bindService(serviceIntent, this, BIND_AUTO_CREATE)
        } catch (exception: Exception) {
            println("****** Binding to service $serviceIntent got error")
            if (!isPackageInstalled()) {
                emitter.onError(ServiceException.NotInstalledException)
            } else {
                emitter.onError(ServiceException.BindException)
            }
        }
        emitter.setCancellable { context.unbindService(this) }
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        println("****** Connected to service $name, service binder $service")
        if (!::emitter.isInitialized) return
        if (emitter.isDisposed) return

        if (service == null) {
            context.unbindService(this)
            return
        }
        emitter.onSuccess(service)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        println("****** Disconnected to service $name")

        if (!::emitter.isInitialized) return
        if (emitter.isDisposed) return
        emitter.onError(ServiceException.DisconnectedException)
    }

    private fun isPackageInstalled(): Boolean {
        val packageName = serviceIntent.component.packageName
        return try {
            context.packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}