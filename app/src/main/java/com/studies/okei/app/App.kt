package com.studies.okei.app

import android.app.Application
import android.content.Context

class App : Application() {
    companion object{
        lateinit var appContext: Context
        const val SHARED_PREFERENCES = "SHARED_PREFERENCES"
        const val tagDatabase = "tagDatabase"
    }
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}