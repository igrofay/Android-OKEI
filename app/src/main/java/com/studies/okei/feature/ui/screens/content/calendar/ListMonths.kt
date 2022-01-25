package com.studies.okei.feature.ui.screens.content.calendar

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.toSize
import com.studies.okei.R
import com.studies.okei.data.entities.Month
import com.studies.okei.feature.ui.theme.AppDimensions
import com.studies.okei.feature.ui.wigets.CircularProgressBar

@Composable
fun ListMonths(
    listMonths: List<Month>,
    openListTeacher: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = AppDimensions.grid_5_5),
        verticalArrangement = Arrangement.spacedBy(AppDimensions.grid_5)
    ){
        items(listMonths){
            ItemMonth(month = it, openMonth = openListTeacher)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemMonth(
    month: Month,
    openMonth: (String)->Unit
){
    val color = colors.primary
    val border =  AppDimensions.border_1
    var expandedState by remember {
        mutableStateOf(false)
    }
    val rotate by animateFloatAsState(
        if (expandedState) 180f else 0f
    )
    Card(
        border = BorderStroke(
            border,color
        ),
        shape = shapes.medium,
        modifier = Modifier
            .fillMaxWidth(0.9f),
        onClick = {openMonth(month.name)},
        backgroundColor = colors.background
    ) {

        Column(
            Modifier
                .padding(
                    top = AppDimensions.grid_5_5 * 2,
                    bottom = AppDimensions.grid_5_5 * 2,
                    start = AppDimensions.grid_5_5 * 1.5f,
                    end = AppDimensions.grid_5_5
                )
                .animateContentSize(
                    animationSpec = tween(
                        easing = LinearOutSlowInEasing
                    )
                )
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(AppDimensions.grid_5_5 * 2.5f)
                        .border(
                            border, color, shapes.medium
                        )
                        .padding(AppDimensions.grid_1_5),
                    contentAlignment = Alignment.Center
                ){
                    Icon(painterResource(
                        if (month.underway) R.drawable.ic_underway
                        else R.drawable.ic_completed
                    ),
                        null,
                        tint = color,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.width(AppDimensions.grid_4_5))
                Column {
                    Text(
                        text = month.name,
                        style = typography.subtitle1,
                        color = color
                    )
                    Text(
                        text ="${stringResource(R.string.last_change)} ${month.lastChange}",
                        style = typography.overline,
                        color = color
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { expandedState = !expandedState }) {
                    Icon(
                        painterResource(R.drawable.ic_down),
                        null,
                        tint = color,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(AppDimensions.grid_5_5*1.5f)
                            .rotate(rotate)
                    )
                }
            }
            if (expandedState) Row(
                modifier = Modifier.padding(top = AppDimensions.grid_5_5*2),
                verticalAlignment = Alignment.CenterVertically
            ){
                var size by remember { mutableStateOf(Size.Zero)}
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .onGloballyPositioned { coordinates ->
                            size = coordinates.size.toSize()
                        },
                    verticalArrangement = Arrangement.spacedBy(AppDimensions.grid_1)
                ) {
                    month.topTeachers.forEachIndexed{
                        index, teac ->
                        ItemTeacher(
                            index = index+1,
                            teacher = teac
                        )
                    }
                }
                Spacer(Modifier.width(AppDimensions.grid_4_5))
                CircularProgressBar(
                    percentage = month.progress,
                    color = color,
                    styleText = typography.h5 ,
                    diameter = with(LocalDensity.current){size.height.toDp()}*1.25f,
                    strokeWidth = border
                )
            }
        }
    }
}

@Composable
fun ItemTeacher(
    index: Int,
    teacher: String
) {
    val color = colors.primary
    val border =  AppDimensions.border_1
    val style = typography.overline

    Row(
        modifier = Modifier
            .border(
                border, color, shapes.small
            )
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(modifier = Modifier
            .fillMaxHeight()
            .width(AppDimensions.grid_3_5 * 2),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "$index",
                color = color,
                style = style,
            )
        }
        Box(modifier = Modifier
            .width(border)
            .fillMaxHeight()
            .background(color))
        Text(
            text = teacher,
            color = color,
            style = style,
            modifier = Modifier
                .padding(
                    vertical = AppDimensions.grid_1_5,
                    horizontal = AppDimensions.grid_2
                )
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            maxLines = 1, overflow = TextOverflow.Ellipsis
        )
    }
}

