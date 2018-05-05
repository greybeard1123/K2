package io.hammerhead.commons.serviceConnection

import android.content.Context
import android.content.Intent
import android.os.IBinder
import io.hammerhead.commons.logging.Logger.serviceConnectionLog
import io.reactivex.Observable
import timber.log.Timber.d

abstract class BaseServiceClientFactory<T>(private val context: Context) : BaseServiceClientFactoryI<T> {
    private val serviceConnection by lazy {  RxServiceConnection(context, getServiceIntent()) }

    override fun serviceConnection(): Observable<T> = Observable.create(serviceConnection).map { client(it) }.doOnDispose { unbindService() }

    protected abstract fun isBackgroundService(): Boolean
    protected abstract fun client(binder: IBinder): T
    protected abstract fun getServiceIntent(): Intent

    private fun unbindService() {
        d(serviceConnectionLog("Unbinding from service ${getServiceIntent().component.shortClassName}"))
        context.unbindService(serviceConnection)
        if (!isBackgroundService()) {
            d(serviceConnectionLog("Stopping service ${getServiceIntent().component.shortClassName}"))
            context.stopService(getServiceIntent())
        }
    }
}