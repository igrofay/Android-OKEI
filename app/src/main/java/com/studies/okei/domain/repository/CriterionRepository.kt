package com.studies.okei.domain.repository

import com.studies.okei.data.entities.Criterion

interface CriterionRepository {
    val listCriterion: List<Criterion> //Список основных критериев
    val moreCriterion: Map<Int, String> // Наименование критериев
}