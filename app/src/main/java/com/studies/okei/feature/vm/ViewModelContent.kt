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
import com.studies.okei.domain.content.ContentUseCase
import com.studies.okei.domain.content.ContentService
import com.studies.okei.domain.repository.CriterionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelContent(
    private val contentService: ContentService,
    private val contentUseCase: ContentUseCase,
    private val criterionRepository: CriterionRepository
): ViewModel() {
    val title = mutableStateOf(R.string.calendar_plan)
    lateinit var user: User
    lateinit var resetUser: ()->Unit

    val say: MutableState<Int?> = mutableStateOf(null)


    private val _listMoths = mutableStateListOf<Month>()
    private val _listTeachers = mutableStateListOf<Teacher>()
    val listMonths: List<Month> get() = _listMoths
    val listTeachers: MutableList<Teacher> get()=_listTeachers

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
                error401 = resetUser,
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
                error401 = resetUser,
                connectionError = {say.value =it }
            )
        }
    }
}