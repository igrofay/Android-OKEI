package com.studies.okei.feature.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Dimensions private constructor(
    coefficient: Float,
    val grid_1: Dp = 2.dp*coefficient,
    val grid_1_5: Dp = 4.dp*coefficient,
    val grid_2: Dp = 6.dp*coefficient,
    val grid_2_5: Dp= 8.dp*coefficient,
    val grid_3: Dp= 10.dp*coefficient,
    val grid_3_5: Dp = 12.dp*coefficient,
    val grid_4: Dp= 14.dp*coefficient,
    val grid_4_5: Dp = 16.dp*coefficient,
    val grid_5: Dp = 18.dp*coefficient,
    val grid_5_5: Dp = 20.dp*coefficient,
    val font_1_5: TextUnit = 10.sp*coefficient,
    val font_2: TextUnit = 12.sp*coefficient,
    val font_2_5: TextUnit = 14.sp*coefficient,
    val font_3: TextUnit = 16.sp*coefficient,
    val font_3_5: TextUnit =18.sp*coefficient,
    val font_4: TextUnit = 20.sp*coefficient,
    val font_4_5: TextUnit = 22.sp*coefficient,
    val font_5: TextUnit = 24.sp*coefficient,
    val font_6_5: TextUnit = 30.sp*coefficient,
    val border_1: Dp = 1.dp*coefficient,
    val corner_1: Dp = 4.dp*coefficient,
    val corner_2: Dp = 8.dp*coefficient,
    val height_top_bar: Dp = 100.dp+20.dp*coefficient
){
    companion object{
        private var thisDimensions: Dimensions? = null
        operator fun invoke(coefficient: Float): Dimensions{
            if (thisDimensions != null) return thisDimensions!!
            thisDimensions = Dimensions(coefficient)
            return thisDimensions!!
        }
    }
}

