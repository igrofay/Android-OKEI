package com.studies.okei.data.state

enum class AccessLevels(val status: String){
    Director("Директор"),
    Admin("Админ"),
    Appraiser("Оценщик"),
    Teacher("Учитель")
}