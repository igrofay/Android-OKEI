package com.studies.okei.feature.ui.wigets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp

@Composable
fun EditText(
    text: String,
    onValueChange: (String)-> Unit,
    textStyle: TextStyle,
    label: String,
    color: Color,
    border: Dp,
    shape: Shape,
    contentPadding: Dp,
    maxWidth: Float
) {
    BasicTextField(
        value = text,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = textStyle.copy(color = color, textAlign = TextAlign.Center),
        cursorBrush = SolidColor(color.copy(0.9f)),
        decorationBox ={ innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth(maxWidth)
                    .border(border, color.copy(0.9f), shape)
                    .padding(contentPadding),
                contentAlignment = Alignment.Center
            ) {
                CompositionLocalProvider(LocalTextSelectionColors provides editTextSelectionColors){
                    if (text.isEmpty())
                        Text(label, style = textStyle.copy(color.copy(0.7f)))
                    innerTextField()
                }
            }
        }
    )
}
val editTextSelectionColors = TextSelectionColors(
    handleColor = Color.White,
    backgroundColor = Color.White.copy(alpha = 0.4f)
)



