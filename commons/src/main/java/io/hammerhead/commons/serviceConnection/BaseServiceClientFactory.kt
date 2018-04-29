package io.hammerhead.commons.serviceConnection

import android.content.Intent
import android.os.IBinder
import io.reactivex.Single

abstract class BaseServiceClientFactory<T> : BaseServiceClientFactoryI<T> {

    override fun serviceConnection(): Single<T> = binderFactory().map { client(it) }

    protected abstract fun binderFactory(): Single<IBinder>
    protected abstract fun client(binder: IBinder): T
    protected abstract fun getServiceIntent(): Intent
}