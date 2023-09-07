package com.faizi.smallworldtest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AppDelegate : Application() {

    override fun onCreate() {
        super.onCreate()

//        if (com.faizi.smallworldtest.BuildConfig.DEBUG)
//            Timber.plant()
    }
}