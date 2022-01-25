package com.studies.okei.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.studies.okei.feature.navigation.StartNavigation
import com.studies.okei.feature.ui.screens.welcome.sign_in.SignInScreen
import com.studies.okei.feature.ui.theme.OKEITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OKEITheme {
                StartNavigation()
            }
        }
    }
}

