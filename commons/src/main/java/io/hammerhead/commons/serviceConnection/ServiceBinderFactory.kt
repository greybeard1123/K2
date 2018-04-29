package io.hammerhead.commons.serviceConnection

import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.orhanobut.logger.Logger
import io.hammerhead.commons.logging.Logger.serviceConnectionLog
import io.reactivex.Single

class ServiceBinderFactory(private val context: Context, private val serviceIntent: Intent) :
        ServiceBinderFactoryI {

    override fun bind(isBackgroundService: Boolean): Single<IBinder> {
        println("****** Inside ServiceBinderFactory bind $context $serviceIntent $isBackgroundService")
        if (!isBackgroundService) context.startService(serviceIntent)

        return Single.create(RxServiceConnection(context, serviceIntent))
                .doFinally { stopService(!isBackgroundService) }
                .doOnError { Logger.e(it, serviceConnectionLog("Error binding to service ${serviceIntent.component.className}")) }
    }

    private fun stopService(isOneTimeTask: Boolean) {
        println("****** Stopping service $isOneTimeTask")
        if (isOneTimeTask) {
            context.stopService(serviceIntent)
        }
    }

}