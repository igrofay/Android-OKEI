package com.studies.okei.feature.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.studies.okei.R

val gilroy = FontFamily(
    Font(R.font.gilroy_light, FontWeight.Light),
    Font(R.font.gilroy_bold, FontWeight.Bold)
)

val Typography @Composable
    get() = Typography(
        defaultFontFamily = gilroy,
        body1 = TextStyle(
            fontSize = AppDimensions.font_4,
            fontFamily = gilroy,
            fontWeight = FontWeight.Light
        ),
        body2= TextStyle(
            fontSize = AppDimensions.font_3_5,
            fontFamily = gilroy,
            fontWeight = FontWeight.Light
        ),
        caption= TextStyle(
            fontSize = AppDimensions.font_2,
            fontFamily = gilroy,
            fontWeight = FontWeight.Light
        ),
        subtitle1 = TextStyle(
            fontSize = AppDimensions.font_4_5,
            fontFamily = gilroy,
            fontWeight = FontWeight.Bold
        ),
        subtitle2 =  TextStyle(
            fontSize = AppDimensions.font_4_5,
            fontFamily = gilroy,
            fontWeight = FontWeight.Light
        ),
        h5= TextStyle(
            fontSize = AppDimensions.font_6_5,
            fontFamily = gilroy,
            fontWeight = FontWeight.Light
        ),
        h6 = TextStyle(
            fontSize = AppDimensions.font_4_5,
            fontFamily = gilroy,
            fontWeight = FontWeight.Light
        ),
        overline=  TextStyle(
            fontSize = AppDimensions.font_1_5,
            fontFamily = gilroy,
            fontWeight = FontWeight.Light
        ),
    )

