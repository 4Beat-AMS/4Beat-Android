package com.fourbeat.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import com.fourbeat.presentation.model.common.MessageCollector
import com.fourbeat.presentation.ui.FourBeatApp
import com.fourbeat.presentation.ui.rememberFourBeatAppState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaunchedEffect(Unit) {
                MessageCollector.messageFlow.collect { message ->
                    showToast(message)
                }
            }

            val appState = rememberFourBeatAppState()
            FourBeatApp(appState = appState)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}