package com.parapaparam.chiphy

import android.app.Application
import com.parapaparam.chiphy.di.AppComponent
import com.parapaparam.chiphy.di.DaggerAppComponent
import com.parapaparam.chiphy.di.ServerModule
import timber.log.Timber


class App : Application() {
    companion object {
        const val BASE_URL = "http://api.giphy.com"
        const val PAGE_SIZE = 20
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        initLogging()
        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .serverModule(ServerModule(BASE_URL))
                .build()
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}