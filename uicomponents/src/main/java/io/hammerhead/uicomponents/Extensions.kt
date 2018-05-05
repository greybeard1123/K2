package io.hammerhead.uicomponents

import android.arch.lifecycle.Lifecycle
import com.uber.autodispose.FlowableSubscribeProxy
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

fun <T> Observable<T>.autoDisposeOnLifecycle(lifecycle: Lifecycle): ObservableSubscribeProxy<T> {
    return this.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycle))
}

fun <T> Flowable<T>.autoDisposeOnLifecycle(lifecycle: Lifecycle): FlowableSubscribeProxy<T> {
    return this.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycle))
}

fun <T> Single<T>.autoDisposeOnLifecycle(lifecycle: Lifecycle): SingleSubscribeProxy<T> {
    return this.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycle))
}