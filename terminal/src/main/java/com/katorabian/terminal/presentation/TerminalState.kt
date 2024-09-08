package com.katorabian.terminal.presentation

import android.os.Parcelable
import com.katorabian.terminal.data.dto.BarDto
import kotlinx.parcelize.Parcelize
import kotlin.math.roundToInt

@Parcelize
data class TerminalState(
    val barList: List<BarDto>,
    val visibleBarsCount: Int = 100,
    val terminalWidth: Float = 0F,
    val scrolledBy: Float = 0F,
): Parcelable {
    val barWidth: Float
        get() = terminalWidth / visibleBarsCount

    val visibleBars: List<BarDto>
        get() {
            val startIndex = (scrolledBy / barWidth).roundToInt().coerceAtLeast(0)
            val endIndex = (startIndex + visibleBarsCount).coerceAtMost(barList.size)
            return barList.subList(startIndex, endIndex)
        }
}