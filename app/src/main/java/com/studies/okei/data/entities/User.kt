package com.studies.okei.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var login: String = "",
    var name: String = "",//удалить
    var imageURL: String = "",//удалить
    var status: String = "",
    var token: String = ""
)