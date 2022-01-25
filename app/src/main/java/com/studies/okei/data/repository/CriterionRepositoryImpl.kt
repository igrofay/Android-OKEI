package com.studies.okei.data.repository

import com.studies.okei.R
import com.studies.okei.app.App
import com.studies.okei.data.datasourse.database.Database
import com.studies.okei.data.entities.Criterion
import com.studies.okei.domain.repository.CriterionRepository
import kotlinx.coroutines.*

class CriterionRepositoryImpl: CriterionRepository{
    private val _listCriterion = mutableListOf<Criterion>()
    override val listCriterion: List<Criterion> get() = _listCriterion
    override val moreCriterion: Map<Int, String> by lazy { getMoreCriterion() }//Могут и вообще не воспользоваться
    init {
        getListCriterion()
    }
    private fun getListCriterion() = runBlocking(Dispatchers.IO) {
        val list = Database.getCriterionDao().getAll()
        _listCriterion.addAll(list)
    }
    @JvmName("getNameCriterion1")
    private fun getMoreCriterion(): MutableMap<Int, String> {
        val response = mutableMapOf<Int,String>()
        App.appContext.resources
            .getStringArray(R.array.name_criteria).forEachIndexed { index, name ->
                response[index+1] = name
        }
        return response
    }
}