package com.studies.okei.feature.ui.theme

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = Gray200,
    primaryVariant = Black900,
    secondary = Green500,
    background = Black900,
    onBackground = Black900,
    surface =  Gray200
)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = Blue700,
    primaryVariant = Blue500,
    secondary = Green500,
    background = Gray200,
    onBackground = Blue700,
    surface =Color.White
)

@Composable
fun OKEITheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}


//TODO Настроить распределнение размеров
val AppDimensions : Dimensions
    @Composable
    get() {
        val screenWidthDp  = LocalConfiguration.current.screenWidthDp
        val coefficient = when{
            screenWidthDp<=540->1f
            screenWidthDp<=720->1.5f
            screenWidthDp<=1080->2f
            else -> 2.5f
        }
        return Dimensions.invoke(coefficient)
    }