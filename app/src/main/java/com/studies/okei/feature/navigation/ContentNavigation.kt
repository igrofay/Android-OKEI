package com.studies.okei.feature.navigation

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.studies.okei.R
import com.studies.okei.data.entities.Teacher
import com.studies.okei.data.entities.VoteCriterion
import com.studies.okei.data.state.AccessLevels
import com.studies.okei.feature.ui.screens.content.calendar.CalendarPlanScreen
import com.studies.okei.feature.ui.screens.content.criteria.CriteriaScreen
import com.studies.okei.feature.ui.screens.content.teachers.TeachersScreen
import com.studies.okei.feature.ui.screens.prizes.PrizesScreen
import com.studies.okei.feature.vm.ViewModelContent


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContentNavigation(
    viewModelContent: ViewModelContent
) {
    val nav = rememberNavController()
    NavHost(
        navController = nav,
        startDestination = ContentRoute.Calendar.route,
        route = StartRoute.Content.route
    ){
        composable(
            ContentRoute.Calendar.route
        ){
            viewModelContent.title.value = stringResource(ContentRoute.Calendar.title)
            val watchAwards = { nav.navigate(ContentRoute.Prizes.route) }
            CalendarPlanScreen(
                _listCurrentMonths = viewModelContent.listMonths,
                requestListMoths = viewModelContent::getListMonths,
                openListTeacher = {
                    nav.navigate(ContentRoute.Teachers.allRoute(it))
                },
                if (viewModelContent.user.status != AccessLevels.Appraiser.status) watchAwards else null
            )
        }
        composable(
            ContentRoute.Prizes.route
        ){
            viewModelContent.title.value = stringResource(ContentRoute.Prizes.title)
            PrizesScreen()
        }
        composable(
            ContentRoute.Teachers.allRoute(),
            arguments = listOf(
                navArgument(ContentRoute.Teachers.arg_month) { type = NavType.StringType }
            )
        ){ backStackEntry ->
            viewModelContent.title.value = stringResource(ContentRoute.Teachers.title)
            val month = backStackEntry.arguments?.getString(ContentRoute.Teachers.arg_month)!!
            TeachersScreen(
                listTeachers = viewModelContent.listTeachers,
                requestListTeachers = {
                    viewModelContent.getListTeacher(month)
                },
                openTeacher = { loginTeacher , nameTeacher->
                    nav.navigate(
                        ContentRoute.Criteria.allRoute(
                            month=month,
                            loginTeacher=loginTeacher,
                            nameTeacher=nameTeacher
                        )
                    )
                }
            )
            BackHandler {
                viewModelContent.listTeachers.clear()
                nav.popBackStack()
            }
        }
        composable(
            ContentRoute.Criteria.allRoute(),
            arguments = listOf(
                navArgument(ContentRoute.Criteria.arg_month) { type = NavType.StringType },
                navArgument(ContentRoute.Criteria.arg_loginTeacher) { type = NavType.StringType },
                navArgument(ContentRoute.Criteria.arg_nameTeacher) { type = NavType.StringType }
            )
        ){  backStackEntry->
            viewModelContent.title.value = stringResource(ContentRoute.Criteria.title)
            val month = backStackEntry.arguments?.getString(ContentRoute.Criteria.arg_month)!!
            val loginTeacher = backStackEntry.arguments?.getString(ContentRoute.Criteria.arg_loginTeacher)!!
            val nameTeacher = backStackEntry.arguments?.getString(ContentRoute.Criteria.arg_nameTeacher)!!
            val putChangeVoteCriterion: (VoteCriterion)-> Unit = {
                viewModelContent.putChangeVoteCriterion(month,loginTeacher, it)
            }
            CriteriaScreen(
                viewModelContent.listCriterion,
                viewModelContent.mapMore,
                nameTeacher,
                requestVoteCriterion = {
                    viewModelContent.getListVoteCriterion(
                        month, loginTeacher
                    )
                },
                viewModelContent.listVoteCriterion,
                putChangeVoteCriterion = if (
                    viewModelContent.user.status != AccessLevels.Director.status
                    && month == viewModelContent.listMonths[0].name
                //массив точно будет не пустым, а 0 item всегда нынешний месяц
                ) putChangeVoteCriterion else null,
                nameAppraiser = viewModelContent.user.name
            )
            BackHandler {
                nav.popBackStack()
                viewModelContent.listVoteCriterion.clear()
            }
        }
    }
}

sealed class ContentRoute(val title: Int, val route: String){
    object Calendar : ContentRoute(R.string.calendar_plan,"calendar_screen")
    object Prizes : ContentRoute(R.string.premium_calculation,"prizes")
    object Teachers: ContentRoute(R.string.list_teachers,"teachers_screen"){
        val arg_month = "month"
        fun allRoute(month: String="{$arg_month}") = "$route/$month"
    }
    object Criteria: ContentRoute(R.string.criteria, "criteria_screen"){
        val arg_month = "month"
        val arg_nameTeacher = "name"
        val arg_loginTeacher = "login"
        fun allRoute(
            month: String="{$arg_month}",
            loginTeacher: String="{$arg_loginTeacher}",
            nameTeacher: String = "{$arg_nameTeacher}"
        ) = "$route/$month/$loginTeacher/$nameTeacher"
    }
}