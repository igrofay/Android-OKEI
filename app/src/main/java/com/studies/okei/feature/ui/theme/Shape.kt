package com.studies.okei.feature.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

val Shapes
    @Composable
    get() = Shapes(
        small = RoundedCornerShape(AppDimensions.corner_1),
        medium = RoundedCornerShape(AppDimensions.corner_2),
        large = RoundedCornerShape(0.dp),
    )