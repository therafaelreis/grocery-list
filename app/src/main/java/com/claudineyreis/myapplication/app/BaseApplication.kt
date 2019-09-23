package com.claudineyreis.myapplication.app

import android.app.Application

class BaseApplication : Application() {

    companion object {
        lateinit var instance: BaseApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}