package io.hammerhead.home

import android.os.Bundle
import io.hammerhead.commons.logging.Logger.homeLog
import io.hammerhead.serviceclients.datastore.DataStoreTestClient
import io.hammerhead.serviceclients.datastore.DataStoreTestClientFactory
import io.hammerhead.uicomponents.autoDisposeOnLifecycle
import io.hammerhead.uicomponents.views.BaseActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit


class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val testFactory = DataStoreTestClientFactory(this).serviceConnection()
        val ticker = Observable.interval(1, TimeUnit.SECONDS)
        Observable.combineLatest(testFactory, ticker,
                BiFunction { client: DataStoreTestClient, tick: Long -> Pair(client, tick) })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { Timber.d(homeLog("Subscribed to service connection")) }
                .autoDisposeOnLifecycle(lifecycle)
                .subscribe({
                    Timber.d(homeLog("Sending message from application"))
                    it.first.sendMessage(it.second.toString())
                }, {
                    Timber.e(it, "Error subscribing to service connection")
                })
    }
}
