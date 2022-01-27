package com.studies.okei.feature.ui.screens.content.criteria

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Checkbox
import androidx.compose.ui.semantics.Role.Companion.RadioButton
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.github.skgmn.composetooltip.*
import com.studies.okei.R
import com.studies.okei.data.entities.Criterion
import com.studies.okei.data.entities.ValueIndicator
import com.studies.okei.data.entities.VoteCriterion
import com.studies.okei.feature.ui.theme.AppDimensions
import com.studies.okei.feature.ui.theme.Gray200
import com.studies.okei.feature.ui.theme.text
import java.util.*

@Composable
fun VoteItem(
    criterion: Criterion,
    more: String,
    voteCriterion: VoteCriterion?=null,
    putChangeVoteCriterion: ((VoteCriterion)->Unit)?=null,
    _nameAppraiser: String?= null
) {
    val mutableVoteCriterion =
        if (voteCriterion==null) null
        else remember {
            voteCriterion.toVoteCriterion()
        }
    val onClickVote: (Int)-> Unit = {  new_point->
        putChangeVoteCriterion?.let { putChange->
            mutableVoteCriterion ?: return@let
            mutableVoteCriterion.apply {
                points.value = new_point
                nameAppraiser.value = _nameAppraiser!!
                lastChange.value = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                     getCurrentDate()
                } else ""
            }
            putChange(mutableVoteCriterion.toVoteCriterion())
        }
    }
    val topRating =  stringResource(
        if (criterion.valueIndicator== ValueIndicator.YesOrNo.name)
            R.string.yes_points
        else R.string.nigh_points,
        criterion.topRating
    )
    val lowestRating = stringResource(
        if (criterion.valueIndicator== ValueIndicator.YesOrNo.name)
            R.string.no_points
        else R.string.average_points,
        criterion.lowestRating
    )
    Column(
        Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight()
    ) {
        Text(
            text = criterion.name,
            color = colors.text,
            style = typography.body1
        )
        Box(
            Modifier.padding(vertical = AppDimensions.grid_2)
        ) {
            var visible by remember {
                mutableStateOf(false)
            }
            ClickableText(
                AnnotatedString(stringResource(R.string.more)),
                style = typography.caption.copy(
                    color = Color.Gray,
                    textDecoration = TextDecoration.Underline
                ),
                onClick = {
                    visible = !visible
                }
            )
            if (visible) TooltipMore(
                text = more
            ){
                visible=false
            }
        }
        Spacer(modifier = Modifier.height(AppDimensions.grid_5_5))
        Row(
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    onClickVote(criterion.topRating)
                }
                .padding(vertical = AppDimensions.grid_1),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(AppDimensions.grid_5_5 * 2)
                    .clip(CircleShape)
                    .background(
                        if (criterion.topRating == mutableVoteCriterion?.points?.value) colors.primary
                        else Color.Transparent
                    )
                    .border(
                        AppDimensions.border_1,
                        if (criterion.topRating ==  mutableVoteCriterion?.points?.value) Color.Transparent
                        else colors.primary,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "1",
                    style = typography.body1,
                    color = if (criterion.topRating ==  mutableVoteCriterion?.points?.value) colors.background
                    else colors.primary
                )
            }
            Text(
                text = topRating,
                style = typography.body1,
                color = colors.text,
                modifier = Modifier.padding(horizontal = AppDimensions.grid_5_5)
            )
        }
        Spacer(modifier = Modifier.height(AppDimensions.grid_5))
        Row(
            modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    onClickVote(criterion.lowestRating)
                }
                .padding(vertical = AppDimensions.grid_1),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(AppDimensions.grid_5_5 * 2)
                    .clip(CircleShape)
                    .background(
                        if (criterion.lowestRating ==  mutableVoteCriterion?.points?.value) colors.primary else Color.Transparent
                    )
                    .border(
                        AppDimensions.border_1,
                        if (criterion.lowestRating == mutableVoteCriterion?.points?.value) Color.Transparent else colors.primary,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "2",
                    style = typography.body1,
                    color = if (criterion.lowestRating ==  mutableVoteCriterion?.points?.value) colors.background else colors.primary
                )
            }
            Text(
                text = lowestRating,
                style = typography.body1,
                color = colors.text,
                modifier = Modifier.padding(horizontal = AppDimensions.grid_5_5)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        mutableVoteCriterion?.let {
            Text(text = "${stringResource(R.string.appraiser)} ${it.nameAppraiser.value}\n"+
                    "${stringResource(R.string.last_change)} ${it.lastChange.value}",
                color = colors.text,
                fontSize = AppDimensions.font_3
            )
        }
    }
}

@Composable
fun TooltipMore(
    text: String,
    onDismissRequest: ()-> Unit
) {
    Tooltip(
        anchorEdge = AnchorEdge.Bottom,
        onDismissRequest = onDismissRequest,
        tooltipStyle = rememberTooltipStyle(
            Color.Gray,
            AppDimensions.corner_1,
            contentPadding = PaddingValues(AppDimensions.grid_1_5)
        ),
        margin = 0.dp,
        tipPosition = EdgePosition(percent= 0f)
    ) {
        Text(
            text = text,
            style = typography.caption,
            color = Gray200
        )
    }
}

@RequiresApi(Build.VERSION_CODES.N)
fun getCurrentDate():String{
    val sdf = SimpleDateFormat("dd.MM.yyyy")
    return sdf.format(Date())
}