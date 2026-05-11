package com.fourbeat.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.fourbeat.presentation.ui.FourBeatApp
import com.fourbeat.presentation.ui.rememberFourBeatAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberFourBeatAppState()
            FourBeatApp(appState = appState)
        }
    }
}