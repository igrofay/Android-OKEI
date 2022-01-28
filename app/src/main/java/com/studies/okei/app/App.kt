package com.studies.okei.app

import android.app.Application
import android.content.Context
import com.studies.okei.data.repository.UserRepositoryImpl

class App : Application() {
    companion object{
        lateinit var appContext: Context
        const val SHARED_PREFERENCES = "SHARED_PREFERENCES"
        const val tagDatabase = "tagDatabase"
    }
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        UserRepositoryImpl.getUserAuthData()
    }
}