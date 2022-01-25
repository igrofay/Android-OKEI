package com.studies.okei.feature.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Black900 = Color(0xFF1D2533)
val Gray200 = Color(0xFFE8E8EE)

val Blue500 = Color(0xFF38BAFE)
val Blue700 = Color(0xFF00569D)

val Orange500 = Color(0xFFFF9F2D)
val Orange700 = Color(0xFFF38D00)

val Green500 = Color(0xFF2DFE8E)

val Colors.OrangeCrown : Color
    get() = if (isLight)  Orange500 else Orange700

val Colors.text: Color
    get() =  if (isLight)  Black900 else Gray200
