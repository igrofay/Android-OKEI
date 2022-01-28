package com.studies.okei.feature.ui.screens.content.calendar

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import com.chargemap.compose.numberpicker.ListItemPicker
import com.studies.okei.R
import com.studies.okei.data.entities.Month
import com.studies.okei.feature.ui.theme.AppDimensions
import com.studies.okei.feature.ui.theme.Gray200
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CalendarPlanScreen(
    _listCurrentMonths: List<Month>,
    requestListMoths: ()->Unit,
    openListTeacher: (String)->Unit,
    watchAwards: (()->Unit)?
) {
    LaunchedEffect(true){
        requestListMoths()
    }
    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange ={
            ModalBottomSheetValue.Expanded != it
        }
    )
    val listMonth = stringArrayResource(R.array.list_months)
    var selectMonth by remember {
        mutableStateOf(listMonth[0])
    }
    BackHandler(state.isVisible) { scope.launch { state.hide() } }
    Box {
        ModalBottomSheetLayout(
            sheetContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f),
                    contentAlignment = Alignment.TopCenter
                ){
                    Box(modifier = Modifier
                        .padding(top = AppDimensions.grid_3)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(0.8f))
                        .width(AppDimensions.grid_5_5 * 2)
                        .height(AppDimensions.grid_2)
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset(y = -AppDimensions.grid_5_5)
                    ) {
                        ListItemPicker(
                            value = selectMonth,
                            onValueChange = {selectMonth = it},
                            list = listMonth.toList(),
                            modifier = Modifier.fillMaxWidth(0.4f),
                            textStyle = typography.subtitle2.copy(colors.primary),
                            dividersColor = colors.primary.copy(0.7f)
                        )
                    }

                }
            },
            sheetState = state,
            scrimColor = Gray200.copy(0.1f),
            sheetBackgroundColor = colors.background
        ) {
            val listCurrentMonths = remember { _listCurrentMonths }
            Box(modifier = Modifier.fillMaxSize()) {
                ListMonths(listMonths = listCurrentMonths, openListTeacher)
                watchAwards?.let {
                    Box(
                        modifier = Modifier
                            .padding(
                                bottom = AppDimensions.grid_4_5,
                                end = AppDimensions.grid_4_5
                            )
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(colors.background)
                            .border(AppDimensions.border_1, colors.primary, RoundedCornerShape(50))
                            .clickable(onClick = it),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_ruble),
                            null,
                            tint = colors.primary,
                            modifier = Modifier
                                .padding(AppDimensions.grid_3_5)
                                .size(AppDimensions.grid_3_5 * 2)
                        )
                    }
                }
            }
        }

        Row(
            Modifier
                .padding(bottom = AppDimensions.grid_4_5)
                .align(Alignment.BottomCenter)
                .clip(CircleShape)
                .background(colors.background)
                .border(AppDimensions.border_1, colors.primary, RoundedCornerShape(50))
                .clickable {
                    if (state.isVisible) openListTeacher(selectMonth)
                    else scope.launch { state.show() }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(R.drawable.ic_calendar),
                null,
                tint = colors.primary,
                modifier = Modifier
                    .padding(AppDimensions.grid_3_5)
                    .size(AppDimensions.grid_3_5 * 2)
            )
            AnimatedVisibility(visible = state.isVisible) {
                Text(
                    text = stringResource(R.string.open),
                    modifier = Modifier.padding(end = AppDimensions.grid_3_5),
                    color = colors.primary
                )
            }
        }

    }
}