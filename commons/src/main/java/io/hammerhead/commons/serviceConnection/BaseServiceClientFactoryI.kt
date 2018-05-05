package io.hammerhead.commons.serviceConnection

import io.reactivex.Observable

interface BaseServiceClientFactoryI<T> {
    fun serviceConnection(): Observable<T>
}