package com.example.myapplication.util

import android.app.Application
import androidx.databinding.ktx.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        PrefManager.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}