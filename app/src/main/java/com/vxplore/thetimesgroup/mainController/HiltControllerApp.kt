package com.vxplore.thetimesgroup.mainController

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltControllerApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}