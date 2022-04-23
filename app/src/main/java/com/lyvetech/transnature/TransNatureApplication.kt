package com.lyvetech.transnature

import android.app.Application
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TransNatureApplication : Application() {

    override fun onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}