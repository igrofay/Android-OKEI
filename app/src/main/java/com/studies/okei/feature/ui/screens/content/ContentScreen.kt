package com.studies.okei.feature.ui.screens.content

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.studies.okei.R
import com.studies.okei.app.appFactoryVM
import com.studies.okei.app.toast
import com.studies.okei.data.entities.User
import com.studies.okei.data.state.AccessLevels
import com.studies.okei.feature.navigation.ContentNavigation
import com.studies.okei.feature.ui.screens.content.criteria.CriteriaScreen
import com.studies.okei.feature.ui.theme.AppDimensions
import com.studies.okei.feature.vm.ViewModelContent

@Composable
fun ContentScreen(
    user: User,
    resetUser: ()-> Unit,
    viewModelContent: ViewModelContent = viewModel(factory = appFactoryVM)
) {
    LaunchedEffect(user){
        viewModelContent.user = user
        viewModelContent.resetUser = resetUser
    }//TODO Вынужденная мера -> Исправить
    val title by remember {
        viewModelContent.title
    }
    var say by remember {
        viewModelContent.say
    }
    LaunchedEffect(say){
        say?.let {
            toast(it)
            say=null
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        ContentTopBar(title)
        if (user.status!=AccessLevels.Teacher.status)
            ContentNavigation(
                viewModelContent
            )
        else {
            viewModelContent.title.value = R.string.criteria
            CriteriaScreen(
                listCriterion = viewModelContent.listCriterion,
                mapMore = viewModelContent.mapMore
            )
        }
    }
}