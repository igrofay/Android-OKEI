package com.studies.okei.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Teacher (
    val login: String,
    val name: String,
    val countPoint: Int,
    val lastChange: String,
    val isKing: Boolean
)