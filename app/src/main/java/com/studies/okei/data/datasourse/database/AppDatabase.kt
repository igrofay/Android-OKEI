package com.studies.okei.data.datasourse.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.studies.okei.data.entities.Criterion

@Database(entities = [Criterion::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun criterionDao(): CriterionDao
}