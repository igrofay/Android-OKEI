package com.studies.okei.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Month(
    val name: String,
    val underway : Boolean, //Идёт ли сейчас = true, если нет = false(месяц прошел),
    val lastChange : String ,
    val progress : Float,
    val topTeachers: List<String> // учителя должны быть отфильтрованными
)