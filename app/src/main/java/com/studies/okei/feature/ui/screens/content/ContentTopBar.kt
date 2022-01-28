package com.studies.okei.feature.ui.screens.content

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.studies.okei.R
import com.studies.okei.feature.ui.theme.AppDimensions
import com.studies.okei.feature.ui.theme.Blue700

@Composable
fun ContentTopBar(
    title: String
) {
    val color = colors.primary
    val border =  AppDimensions.border_1
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(AppDimensions.height_top_bar),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(bottom = AppDimensions.grid_2)
        ){
            Box(modifier = Modifier
                .height(border)
                .weight(0.5f)
                .background(color)
            )
            Text(
                text = title,
                color = color,
                style = typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier.border(
                    border,
                    color,
                    RoundedCornerShape(0, 15, 0, 15)
                ).weight(3.5f)
                .padding(AppDimensions.grid_4_5)
            )
            Box(modifier = Modifier
                .height(border)
                .weight(0.5f)
                .background(color)
                .align(Alignment.Bottom)
            )
        }
    }
}