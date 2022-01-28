package com.studies.okei.app

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.annotation.StringRes


fun toast(
    @StringRes strRes: Int
){
    Toast.makeText(App.appContext, strRes, Toast.LENGTH_SHORT).show()
}


//Нужно поставлять каждый раз новый SharedPreferences
fun getAppSP(): SharedPreferences = App.appContext
    .getSharedPreferences(App.SHARED_PREFERENCES , Context.MODE_PRIVATE)

//Один FactoryVM на приложение
val appFactoryVM : AppVMFactory by lazy {
    AppVMFactory()
}