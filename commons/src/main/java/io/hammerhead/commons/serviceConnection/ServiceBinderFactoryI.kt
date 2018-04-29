package io.hammerhead.commons.serviceConnection

import android.os.IBinder
import io.reactivex.Single

interface ServiceBinderFactoryI {
    fun bind(isBackgroundService: Boolean): Single<IBinder>
}