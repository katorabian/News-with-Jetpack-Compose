package com.katorabian.terminal.presentation

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.katorabian.terminal.data.dto.BarDto
import kotlin.math.roundToInt

data class TerminalState(
    val barList: List<BarDto>,
    val visibleBarsCount: Int = 100,
    val terminalWidth: Float = 0F,
    val scrolledBy: Float = 0F,
) {
    val barWidth: Float
        get() = terminalWidth / visibleBarsCount

    val visibleBars: List<BarDto>
        get() {
            val startIndex = (scrolledBy / barWidth).roundToInt().coerceAtLeast(0)
            val endIndex = (startIndex + visibleBarsCount).coerceAtMost(barList.size)
            return barList.subList(startIndex, endIndex)
        }
}