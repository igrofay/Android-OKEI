package com.studies.okei.data.datasourse.database

import androidx.room.Room
import com.studies.okei.app.App

internal object Database {
    private const val path_db = "database/criterion.db"
    private val db by lazy {
        Room.databaseBuilder(
            App.appContext,
            AppDatabase::class.java, App.tagDatabase
        ).createFromAsset(path_db).build()
    }
    fun getCriterionDao() = db.criterionDao()
}