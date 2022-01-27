package com.studies.okei.data.entities

import androidx.compose.runtime.MutableState

class MutableVoteCriterion (
    private val id: String,
    var nameAppraiser: MutableState<String>,
    var lastChange: MutableState<String>,
    var points: MutableState<Int>,
){
    fun toVoteCriterion() = VoteCriterion(id,nameAppraiser.value,lastChange.value,points.value)
}