package com.studies.okei.data.repository

import androidx.compose.runtime.mutableStateOf
import com.studies.okei.app.getAppSP
import com.studies.okei.data.entities.User
import com.studies.okei.data.entities.UserAuthData
import com.studies.okei.domain.repository.UserRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserRepositoryImpl : UserRepository{
    private val KEY_AUT = "KET_AUT"

    private val _user = mutableStateOf<User?>(null)
    override val user
        get() = _user
    private val _userAuthData = mutableStateOf<String?>(null)
    override val userAuthData
        get()= _userAuthData

    init{ getUserAuthData() }
    override fun getUserAuthData() {
        _userAuthData.value = getAppSP()
            .getString(KEY_AUT, null)
    }
    override fun saveUserDataAut(dataAut: UserAuthData?){
        val data = dataAut?.let {
            Json.encodeToString(it)
        }
        _userAuthData.value = data
        getAppSP().edit()
            .putString(KEY_AUT,data).apply()
    }
    override fun setUser(user: User?) {
        _user.value = user
    }
}