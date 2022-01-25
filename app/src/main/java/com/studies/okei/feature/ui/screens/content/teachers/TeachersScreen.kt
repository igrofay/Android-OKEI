package com.studies.okei.feature.ui.screens.content.teachers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.toSize
import com.studies.okei.R
import com.studies.okei.data.entities.Teacher
import com.studies.okei.feature.ui.theme.AppDimensions
import com.studies.okei.feature.ui.theme.OrangeCrown
import com.studies.okei.feature.ui.wigets.EditText
import kotlinx.coroutines.delay

@Composable
fun TeachersScreen(
    listTeachers: List<Teacher>,
    requestListTeachers: ()->Unit,
    openTeacher: (login: String,name: String)-> Unit
) {
    LaunchedEffect(true){
        requestListTeachers()
    }
    var search by remember {
        mutableStateOf("")
    }
    val searchList = remember {
        mutableStateListOf<Teacher>()
    }

    if (search.isEmpty()){
        searchList.clear()
        searchList.addAll(listTeachers)
    }else{
        searchList.clear()
        listTeachers.forEach {
            if (it.name.lowercase().contains(search.lowercase()))
                searchList.add(it)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(AppDimensions.grid_4))
        EditText(
            text = search,
            onValueChange = {search=it},
            textStyle = typography.body2,
            label = stringResource(R.string.search),
            color = colors.primary ,
            border = AppDimensions.border_1,
            shape = shapes.medium,
            contentPadding = AppDimensions.grid_4,
            maxWidth = 0.9f
        )
        Spacer(modifier = Modifier.height(AppDimensions.grid_4))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = AppDimensions.grid_5_5),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.grid_5)
        ){
            items(searchList){
                ItemTeacher(
                    teacher = it,
                    openTeacher = openTeacher
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemTeacher(
    teacher: Teacher,
    openTeacher: (login: String, name: String)-> Unit
) {
    val color = colors.primary
    Card(
        onClick = {
             openTeacher(teacher.login,teacher.name)
        },
        backgroundColor = colors.background,
        border = BorderStroke(AppDimensions.border_1, color),
        shape = shapes.medium,
        modifier = Modifier.fillMaxWidth(0.9f)
    ) {
        var size by remember { mutableStateOf(Size.Zero) }
        val heigth = with(LocalDensity.current) { size.height.toDp() }
        Row(
            Modifier.padding(
                top = AppDimensions.grid_5_5 ,
                bottom = AppDimensions.grid_5_5,
                start = AppDimensions.grid_5_5 * 1.5f,
                end = AppDimensions.grid_5_5 *0.5f
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                Modifier.onGloballyPositioned { coordinates ->
                    size = coordinates.size.toSize()
                }.weight(1f)
            ) {
                Text(
                    text = teacher.name,
                    color = color,
                    style = typography.h6,
                    maxLines = 1, overflow = TextOverflow.Ellipsis
                )
                Text(
                    text ="${stringResource(R.string.last_change)} ${teacher.lastChange}",
                    color=color,
                    style = typography.overline,
                    maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }
            Column(
                Modifier
                    .size(heigth * 2.25f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painterResource(R.drawable.ic_crown),
                    null,
                    colorFilter = ColorFilter.tint(
                        if(teacher.isKing) colors.OrangeCrown else Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .weight(1f)
                        .padding(bottom = AppDimensions.grid_1)
                )
                Box(modifier = Modifier
                    .height(heigth * 1.5f)
                    .width(heigth * 1.5f)
                    .border(AppDimensions.border_1, color, CircleShape),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "${teacher.countPoint}",
                        color = color,
                        style = typography.h6,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}