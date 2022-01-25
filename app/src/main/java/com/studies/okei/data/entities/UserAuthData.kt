package com.studies.okei.data.entities
import kotlinx.serialization.Serializable

@Serializable
data class UserAuthData(
    val login: String,
    val password: String
)