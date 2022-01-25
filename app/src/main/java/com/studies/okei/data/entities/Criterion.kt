package com.studies.okei.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "criterion")
class Criterion(
    @PrimaryKey
    var id: String = "",
    var idParent: Int = 0,
    var name: String = "",
    var valueIndicator: String = "" ,
    var topRating : Int = 0,
    var lowestRating : Int = 0
)

enum class ValueIndicator{
    YesOrNo,
    HighOrAverage
}