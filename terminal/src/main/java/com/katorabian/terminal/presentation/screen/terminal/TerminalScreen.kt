package com.katorabian.terminal.presentation.screen.terminal

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Preview
@Composable
fun TerminalScreen() {
    val viewModel: TerminalViewModel = viewModel()
    val screenState = viewModel.state.collectAsState()

    when (val currState = screenState.value) {
        is TerminalScreenState.Content -> {
            Log.d("TerminalScreen", currState.barList.toString())
        }

        is TerminalScreenState.Initial -> {

        }
    }
}