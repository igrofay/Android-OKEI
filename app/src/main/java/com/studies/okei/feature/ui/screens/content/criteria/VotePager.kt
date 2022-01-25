package com.studies.okei.feature.ui.screens.content.criteria

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.studies.okei.R
import com.studies.okei.data.entities.Criterion
import com.studies.okei.feature.ui.theme.AppDimensions
import com.studies.okei.feature.ui.theme.Orange700
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun VotePager(
    listCriterion : List<Criterion>,
    openVisibleItem: Int,
    mapMore: Map<Int,String>,
    nameTeacher: String?=null,
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val visibleItem = pagerState.currentPage
    val anim: (Int)-> Unit = {
        scope.launch { pagerState.animateScrollToPage(it) }
    }
    LaunchedEffect(openVisibleItem){
        anim(openVisibleItem)
    }
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .padding(vertical = AppDimensions.grid_3_5)
            .clip(CircleShape)
            .background(Color.Gray.copy(0.8f))
            .width(AppDimensions.grid_5_5 * 2)
            .height(AppDimensions.grid_2)
        )
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },
            backgroundColor = Color.Transparent,
            contentColor = colors.primary
        ) {
            listCriterion.forEachIndexed{ index, criterion ->
                val selected = pagerState.currentPage == index
                Tab(
                    selected = selected,
                    onClick = {
                        anim(index)
                    },
                    selectedContentColor= colors.primary,
                ) {
                    Box(
                        modifier = Modifier
                            .size(AppDimensions.grid_5_5 * 3)
                            .padding(AppDimensions.grid_3)
                            .clip(CircleShape)
                            .background(
                                if (selected) colors.primary else Color.Transparent
                            )
                            .border(
                                AppDimensions.border_1,
                                if (selected) Color.Transparent else colors.primary,
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = criterion.id,
                            style = typography.caption,
                            color = if (selected) colors.background else colors.primary
                        )
                    }
                }
            }
        }
        HorizontalPager(
            count = listCriterion.size,
            state = pagerState,
            modifier = Modifier
                .weight(1f),
            contentPadding = PaddingValues(
                top = AppDimensions.grid_5_5
            )
        ) { page ->
            val criterion = listCriterion[page]
            VoteItem(
                criterion = criterion,
                more = mapMore[criterion.idParent]!!
            )
        }
        Row(
            Modifier
                .fillMaxWidth(0.9f).height(IntrinsicSize.Min)
                .padding(vertical = AppDimensions.grid_5_5),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .clip(CircleShape)
                    .border(
                        AppDimensions.border_1,
                        colors.primary,
                        CircleShape
                    )
                    .clickable {
                        if (visibleItem - 1 >= 0)
                            anim(visibleItem - 1)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(R.drawable.ic_right),
                    null,
                    tint = colors.primary,
                    modifier = Modifier
                        .padding(AppDimensions.grid_3_5)
                        .size(AppDimensions.grid_3_5 * 2)
                )
            }
            Box(
                Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ){
                nameTeacher?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight().fillMaxWidth(0.8f)
                            .border(AppDimensions.border_1, colors.primary,CircleShape)
                            .padding(horizontal = AppDimensions.grid_3),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = it,
                            style = typography.body2,
                            color = colors.primary,
                            maxLines = 1, overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Box(
                Modifier
                    .clip(CircleShape)
                    .border(
                        AppDimensions.border_1,
                        colors.primary,
                        CircleShape
                    )
                    .clickable {
                        if (visibleItem + 1 < listCriterion.size)
                            anim(visibleItem + 1)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(R.drawable.ic_left),
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

