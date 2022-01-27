package com.studies.okei.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var login: String = "",
    var name: String = "",
    var status: String = "",
    var token: String = ""
)