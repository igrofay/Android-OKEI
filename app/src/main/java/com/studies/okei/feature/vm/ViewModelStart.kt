package com.studies.okei.feature.vm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.work.WorkManager
import com.studies.okei.R
import com.studies.okei.app.App
import com.studies.okei.data.entities.UserAuthData
import com.studies.okei.domain.authorization.AuthenticationObserver
import com.studies.okei.domain.authorization.WorkAuthorization
import com.studies.okei.domain.repository.UserRepository

class ViewModelStart(
    private val userRepository: UserRepository
) : ViewModel() {
    val user = userRepository.user
    val userAuthData = userRepository.userAuthData
    private val _say: MutableState<Int?> = mutableStateOf(null)

    val say: State<Int?> get() =  _say

    private val autObserver = AuthenticationObserver(
        success = userRepository::setUser,
        error = ::errorAut
    )

    fun resetUser() { userRepository.setUser(null) }

    private fun errorAut(){
        userRepository.saveUserDataAut(null)
        _say.value = R.string.wrong_data
    }

    fun authentication(){
        val work = WorkAuthorization.newInstance(userAuthData.value!!)
        WorkManager.getInstance(App.appContext).apply {
            enqueue(work)
            getWorkInfoByIdLiveData(work.id).observeForever(autObserver)
        }
    }

    fun checkInputData(login: String, password: String){
        when{
            login.isEmpty() && password.isEmpty()-> _say.value = R.string.input_data
            login.isEmpty() ->  _say.value = R.string.input_login
            password.isEmpty()-> _say.value = R.string.input_password
            else->userRepository.saveUserDataAut(
                UserAuthData(login, password)
            )
        }
    }
}