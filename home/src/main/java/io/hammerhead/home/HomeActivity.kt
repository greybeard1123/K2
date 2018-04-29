package io.hammerhead.home

import android.os.Bundle
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import io.hammerhead.commons.logging.Logger.homeLog
import io.hammerhead.serviceclients.datastore.DataStoreTestClientFactory
import io.hammerhead.uicomponents.views.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Logger.addLogAdapter(AndroidLogAdapter())

        val testFactory = DataStoreTestClientFactory(this)
        testFactory.serviceConnection()
                .doOnSubscribe { println("****** Subscribed to this dude") }
                .doOnDispose { println("****** Disposed this dude") }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    println("******* Home got message : ${it.message}")
                    println("******* Home sending message test")
                    it.sendMessage("TEST from HOME")
                }, {
                    println("****** Got error ${it.printStackTrace()}")
                    Logger.e(it, homeLog("Error subscribing to testFactory"))
                })
    }
}
