package io.hammerhead.commons.serviceConnection

import io.reactivex.Single

interface BaseServiceClientFactoryI<T> {
    fun serviceConnection(): Single<T>
}