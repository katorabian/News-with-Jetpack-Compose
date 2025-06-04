package com.katorabian.terminal.presentation

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.katorabian.terminal.data.dto.BarDto
import kotlinx.parcelize.Parcelize
import kotlin.math.roundToInt

@Parcelize
data class TerminalState(
    val barList: List<BarDto>,
    val visibleBarsCount: Int = 100,
    val terminalWidth: Float = 1F,
    val terminalHeight: Float = 1F,
    val scrolledBy: Float = 0F,
): Parcelable {
    val barWidth: Float
        get() = terminalWidth / visibleBarsCount

    private val visibleBars: List<BarDto>
        get() {
            val startIndex = (scrolledBy / barWidth).roundToInt().coerceAtLeast(0)
            val endIndex = (startIndex + visibleBarsCount).coerceAtMost(barList.size)
            return barList.subList(startIndex, endIndex)
        }

    val max: Float
        get() = visibleBars.maxOf { it.high }
    val min: Float
        get() = visibleBars.minOf { it.low }
    val pxPerPoint: Float
        get() = terminalHeight / (max - min)
}

@Composable
fun rememberTerminalState(bars: List<BarDto>): MutableState<TerminalState> {
    return rememberSaveable {
        mutableStateOf(TerminalState(bars))
    }
}