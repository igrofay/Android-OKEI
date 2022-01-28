package com.studies.okei.data.state

enum class AccessLevels(val status: String){
    Director("Директор"),
    Admin("Админ"),
    Appraiser("Оценивающий"),
    Teacher("Учитель")
}