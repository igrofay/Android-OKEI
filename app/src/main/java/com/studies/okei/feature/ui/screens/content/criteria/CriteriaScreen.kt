package com.studies.okei.feature.ui.screens.content.criteria

import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.accompanist.pager.HorizontalPager
import com.studies.okei.data.entities.Criterion
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CriteriaScreen(
    listCriterion : List<Criterion>,
    mapMore: Map<Int,String>,
    nameTeacher: String?=null,
) {
    val scope = rememberCoroutineScope()
    val state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange ={
            ModalBottomSheetValue.HalfExpanded!= it
        }
    )
    var visibleItem by rememberSaveable{
        mutableStateOf(0)
    }
    ModalBottomSheetLayout(
        sheetContent = {
            VotePager(
                listCriterion,
                visibleItem,
                mapMore,
                nameTeacher
            )
        },
        sheetState = state,
        sheetBackgroundColor = colors.background
    ){
        ListCriteria(listCriterion = listCriterion){
            visibleItem = it
            scope.launch { state.showAll() }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
private suspend fun ModalBottomSheetState.showAll() {
    animateTo(targetValue = ModalBottomSheetValue.Expanded)
}