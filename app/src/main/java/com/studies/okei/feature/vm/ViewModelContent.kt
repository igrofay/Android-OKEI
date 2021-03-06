package com.studies.okei.feature.vm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studies.okei.R
import com.studies.okei.data.entities.Month
import com.studies.okei.data.entities.Teacher
import com.studies.okei.data.entities.User
import com.studies.okei.data.entities.VoteCriterion
import com.studies.okei.domain.content.ContentUseCase
import com.studies.okei.domain.content.ContentService
import com.studies.okei.domain.repository.CriterionRepository
import com.studies.okei.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelContent(
    private val userRepository: UserRepository,
    private val contentService: ContentService,
    private val contentUseCase: ContentUseCase,
    criterionRepository: CriterionRepository
): ViewModel() {
    val title = mutableStateOf("")

    val user: User get() = userRepository.user.value!!
    private fun resetUser(){
        userRepository.setUser(null)
    }

    val say: MutableState<Int?> = mutableStateOf(null)


    private val _listMoths = mutableStateListOf<Month>()
    private val _listTeachers = mutableStateListOf<Teacher>()
    val listMonths: List<Month> get() = _listMoths
    val listTeachers: MutableList<Teacher> get()=_listTeachers

    private val _listVoteCriterion = mutableStateListOf<VoteCriterion>()
    val listVoteCriterion : MutableList<VoteCriterion> get() = _listVoteCriterion

    val listCriterion = criterionRepository.listCriterion
    val mapMore = criterionRepository.moreCriterion
    fun getListMonths(){
        viewModelScope.launch(Dispatchers.IO){
            contentUseCase.processResponse<List<Month>>(
                httpResponse = { contentService.calendarPlan(user.token) },
                success = {
                    _listMoths.clear()
                    _listMoths.addAll(it)
                },
                error401 = ::resetUser,
                connectionError = {say.value =it }
            )
        }
    }
    fun getListTeacher(nameMonth: String){
        viewModelScope.launch(Dispatchers.IO){
            contentUseCase.processResponse<List<Teacher>>(
                httpResponse = { contentService.listTeacher(user.token, nameMonth)},
                success = {
                    _listTeachers.clear()
                    _listTeachers.addAll(it)
                },
                error401 = ::resetUser,
                connectionError = {say.value =it }
            )
        }
    }
    fun getListVoteCriterion(
        nameMonth: String,
        loginTeacher: String
    ){
        viewModelScope.launch(Dispatchers.IO){
            contentUseCase.processResponse<List<VoteCriterion>>(
                httpResponse = { contentService.listVoteCriterion(user.token,nameMonth, loginTeacher) },
                success = {
                    _listVoteCriterion.clear()
                    _listVoteCriterion.addAll(it)
                },
                error401 = ::resetUser,
                connectionError = {say.value =it }
            )
        }
    }
    fun putChangeVoteCriterion(
        nameMonth: String,
        loginTeacher: String,
        changeVoteCriterion: VoteCriterion
    ){
        viewModelScope.launch(Dispatchers.IO) {
            contentUseCase.processResponseForPut(
                httpResponse = {
                    contentService.putChangeVoteCriterion(
                        user.token,
                        nameMonth ,
                        loginTeacher,
                        changeVoteCriterion
                    )
                },
                success = {
                    _listVoteCriterion.forEach {
                        if(it.id == changeVoteCriterion.id){
                            it.transplantation(changeVoteCriterion)
                            return@forEach
                        }
                    }
                    say.value = R.string.chage
                },
                error401 = ::resetUser,
                connectionError = {say.value =it }
            )
        }
    }
}