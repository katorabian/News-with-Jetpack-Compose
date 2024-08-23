package com.katorabian.terminal.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.katorabian.terminal.presentation.screen.terminal.TerminalScreen
import com.katorabian.terminal.presentation.ui.theme.TerminalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TerminalTheme {
                TerminalScreen()
            }
        }
    }
}