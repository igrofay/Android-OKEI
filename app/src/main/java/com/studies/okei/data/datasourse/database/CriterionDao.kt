package com.studies.okei.data.datasourse.database

import androidx.room.Dao
import androidx.room.Query
import com.studies.okei.data.entities.Criterion

@Dao
interface CriterionDao {
    @Query("SELECT * FROM criterion")
    fun getAll(): List<Criterion>
}