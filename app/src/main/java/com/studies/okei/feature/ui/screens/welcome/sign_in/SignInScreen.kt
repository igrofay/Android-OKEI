package com.studies.okei.feature.ui.screens.welcome.sign_in

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import com.studies.okei.R
import com.studies.okei.app.toast
import com.studies.okei.feature.ui.theme.AppDimensions
import com.studies.okei.feature.ui.wigets.EditText

@Composable
fun SignInScreen(
    onClickButton: (login: String, password:String)->Unit,
    _say: State<Int?>
) {
    var login by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    val shape = shapes.medium
    val color = colors.surface
    val maxWidth = 0.7f
    val styleMainText = typography.body1
    val contentPadding = AppDimensions.grid_4_5
    val say by remember { _say }
    LaunchedEffect(say){
        say?.let { toast(it) }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier
                    .scale(1.5f, 1f)
                    .fillMaxSize()
                    .background(
                        color, RoundedCornerShape(0, 0, 100, 100)
                    )
            )
            Image(
                painter = painterResource(
                    if (isSystemInDarkTheme())
                        R.drawable.ic_preview_night
                    else
                        R.drawable.ic_preview_light
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(maxWidth)
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(0.25f))
            EditText(
                text = login,
                onValueChange = { login = it },
                textStyle = styleMainText,
                label = stringResource(R.string.login),
                color = color,
                border = AppDimensions.border_1 ,
                shape = shape ,
                contentPadding = contentPadding,
                maxWidth = maxWidth
            )
            Spacer(modifier = Modifier.height(AppDimensions.grid_3_5))
            EditText(
                text = password,
                onValueChange = { password = it },
                textStyle = styleMainText,
                label = stringResource(R.string.password),
                color = color,
                border = AppDimensions.border_1,
                shape = shape,
                contentPadding = contentPadding,
                maxWidth = maxWidth
            )
            Spacer(Modifier.weight(0.3f))
            Button(
                onClick = { onClickButton(login, password) },
                modifier = Modifier.fillMaxWidth(maxWidth),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = color
                ),
                shape = shape,
                contentPadding = PaddingValues(contentPadding),
            ) {
                Text(stringResource(R.string.sign_in),
                    color = colors.onBackground, style = styleMainText)
            }
            Spacer(Modifier.weight(0.15f))
            val context = LocalContext.current
            ClickableText(
                text = AnnotatedString(stringResource(R.string.advertisement)),
                style = typography.caption.copy(
                    textAlign = TextAlign.Center, textDecoration = TextDecoration.Underline,
                    color = color
                ),
                modifier = Modifier
                    .fillMaxWidth(maxWidth)
                    .padding(bottom = AppDimensions.grid_3_5),
                onClick = {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://oksei.ru/")
                        )
                    )
                }
            )
        }
    }
}

