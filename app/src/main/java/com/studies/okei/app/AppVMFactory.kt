package com.studies.okei.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.studies.okei.data.datasourse.web.Web
import com.studies.okei.data.repository.CriterionRepositoryImpl
import com.studies.okei.data.repository.UserRepositoryImpl
import com.studies.okei.domain.content.ContentUseCase
import com.studies.okei.feature.vm.ViewModelContent
import com.studies.okei.feature.vm.ViewModelStart

class AppVMFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass){
            ViewModelStart::class.java-> ViewModelStart(UserRepositoryImpl()) as T
            ViewModelContent::class.java->ViewModelContent(
                Web, ContentUseCase(), CriterionRepositoryImpl()
            ) as T
            else-> throw IllegalAccessException("No VM")
        }
    }
}