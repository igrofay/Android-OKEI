package com.studies.okei.feature.ui.wigets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
fun CircularProgressBar(
    percentage: Float,
    color: Color,
    styleText: TextStyle,
    diameter: Dp,
    strokeWidth : Dp
) {
    Box(
        Modifier
            .size(diameter),
        Alignment.Center
    ){
        Canvas(modifier = Modifier.fillMaxSize(0.95f)){
            drawArc(
                color = Color.Gray.copy(0.5f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(strokeWidth.toPx() , cap = StrokeCap.Round)
            )
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 3.6f*percentage,
                useCenter = false,
                style = Stroke(strokeWidth.toPx() , cap = StrokeCap.Round)
            )
        }
        Text(
            text ="${percentage.toInt()}%" ,
            style = styleText,
            color = color
        )
    }
}