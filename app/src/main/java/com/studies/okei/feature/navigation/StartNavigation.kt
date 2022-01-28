package com.studies.okei.feature.navigation

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.studies.okei.R
import com.studies.okei.app.appFactoryVM
import com.studies.okei.data.state.AccessLevels
import com.studies.okei.feature.ui.screens.content.ContentScreen
import com.studies.okei.feature.ui.screens.welcome.sign_in.SignInScreen
import com.studies.okei.feature.ui.screens.welcome.splash.SplashScreen
import com.studies.okei.feature.vm.ViewModelStart

@Composable
fun StartNavigation(
    viewModelStart: ViewModelStart = viewModel(factory = appFactoryVM)
) {
    val navController = rememberNavController()
    val user by remember {
        viewModelStart.user
    }
    val userAuthData by remember {
        viewModelStart.userAuthData
    }
    val activity = LocalContext.current as Activity
    BackHandler(onBack = activity::finish)
    NavHost(
        navController = navController,
        startDestination = StartRoute.Splash.route,
        modifier = Modifier
            .background(colors.onBackground)
    ){
        composable(StartRoute.Splash.route){
            userAuthData?.let {
                SplashScreen(viewModelStart::authentication)
            } ?: LaunchedEffect(true ){
                navController.navigate(StartRoute.SignIn.route)
            }
            if (user!=null) LaunchedEffect(true ){
                navController.navigate(StartRoute.Content.route)
            }
        }
        composable(StartRoute.SignIn.route){
            userAuthData?.let {
                LaunchedEffect(true){ navController.popBackStack()}
            } ?: SignInScreen(
                viewModelStart::checkInputData,
                viewModelStart.say
            )
        }
        composable(StartRoute.Content.route){
            user?.let {
                ContentScreen(
                    if (it.status!=AccessLevels.Teacher.status) stringResource(R.string.calendar_plan)
                    else stringResource(R.string.criteria)
                )
            } ?: LaunchedEffect(true){ navController.popBackStack()}
        }
    }

}

sealed class StartRoute(val route: String){
    object Splash : StartRoute("splash_screen")
    object SignIn : StartRoute("sign_in_screen")
    object Content : StartRoute("start_screen")
}