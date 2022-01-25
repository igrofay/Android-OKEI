package com.studies.okei.feature.ui.screens.content.criteria

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.studies.okei.R
import com.studies.okei.data.entities.Criterion
import com.studies.okei.feature.ui.theme.AppDimensions
import com.studies.okei.feature.ui.theme.text
import com.studies.okei.feature.ui.wigets.EditText

@Composable
fun ListCriteria(
    listCriterion : List<Criterion>,
    openCriterion:(Int)->Unit
) {
    var search by remember {
        mutableStateOf("")
    }
    val searchList = remember {
        mutableStateListOf<Criterion>()
    }
    if (search.isEmpty()) {
        searchList.clear()
        searchList.addAll(listCriterion)
    }else{
        searchList.clear()
        listCriterion.forEach {
            if (
                it.name.lowercase().contains(search.lowercase())
                || it.id.contains(search)
            ){
                searchList.add(it)
            }
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
            shape = MaterialTheme.shapes.medium,
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
            itemsIndexed(searchList){ index, item ->
                ItemCriterion(
                    id = item.id,
                    name =item.name,
                    isStroke = index%2==0
                ){
                    openCriterion(
                        listCriterion.indexOf(item)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemCriterion(
    id: String,
    name: String,
    isStroke: Boolean,
    openCriterion: () -> Unit
) {
    Card(
        onClick = openCriterion,
        backgroundColor = colors.background,
        border = BorderStroke(AppDimensions.border_1,colors.primary),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(IntrinsicSize.Min)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){

            Box(modifier = Modifier.size(AppDimensions.grid_5_5*4)
                .padding(AppDimensions.grid_5)
                .clip(CircleShape)
                .background(
                    if (isStroke) Color.Transparent else colors.primary
                )
                .border(
                    AppDimensions.border_1,
                    if (isStroke) colors.primary else Color.Transparent,
                    CircleShape
                ), contentAlignment = Alignment.Center){
                Text(
                    text = id,
                    style = typography.caption,
                    maxLines = 1,
                    color = if (isStroke) colors.primary else colors.background,
                )
            }
            Box(modifier = Modifier
                .fillMaxHeight()
                .width(AppDimensions.border_1)
                .background(colors.primary))
            Text(
                text = name,
                fontSize = AppDimensions.font_2_5,
                color = colors.text,
                modifier = Modifier.padding(
                    horizontal = AppDimensions.grid_5_5,
                    vertical = AppDimensions.grid_4
                )
            )
        }
    }
}