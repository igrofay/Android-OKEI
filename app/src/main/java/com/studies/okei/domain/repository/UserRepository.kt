package com.studies.okei.domain.repository

import androidx.compose.runtime.State
import com.studies.okei.data.entities.User
import com.studies.okei.data.entities.UserAuthData

interface UserRepository {
    val user: State<User?>
    val userAuthData : State<String?>
    fun getUserAuthData()
    fun saveUserDataAut(dataAut: UserAuthData?)
    fun setUser(user: User?)
}