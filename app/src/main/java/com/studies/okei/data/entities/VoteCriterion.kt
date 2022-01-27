package com.studies.okei.data.entities

import androidx.compose.runtime.mutableStateOf
import kotlinx.serialization.Serializable

@Serializable
class VoteCriterion(
    val id: String,
    var nameAppraiser: String,
    var lastChange: String,
    var points: Int,
){
    fun toVoteCriterion() = MutableVoteCriterion(
        id = id,
        nameAppraiser = mutableStateOf(nameAppraiser),
        lastChange= mutableStateOf(lastChange),
        points = mutableStateOf(points)
    )
    fun transplantation(voteCriterion: VoteCriterion){
        this.lastChange = voteCriterion.lastChange
        this.nameAppraiser = voteCriterion.nameAppraiser
        this.points = voteCriterion.points
    }
}