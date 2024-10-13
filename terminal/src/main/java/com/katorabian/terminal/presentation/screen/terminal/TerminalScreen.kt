package com.katorabian.terminal.presentation.screen.terminal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Preview
@Composable
fun TerminalScreen() {
    val viewModel: TerminalViewModel = viewModel()
    val screenState = viewModel.state.collectAsState()

    when (val currState = screenState.value) {
        is TerminalScreenState.Content -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.size(350.dp)) {
                    TerminalGraphic(bars = currState.barList)
                }
            }
        }

        is TerminalScreenState.Initial -> {

        }
    }
}