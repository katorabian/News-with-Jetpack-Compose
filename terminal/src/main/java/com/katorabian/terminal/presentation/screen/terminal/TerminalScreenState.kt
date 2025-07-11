package com.katorabian.terminal.presentation.screen.terminal

import com.katorabian.terminal.data.dto.BarDto

sealed class TerminalScreenState {

    data object Initial: TerminalScreenState()

    data object Loading: TerminalScreenState()

    data class Content(
        val barList: List<BarDto>,
        val timeFrame: TimeFrame
    ): TerminalScreenState()
}