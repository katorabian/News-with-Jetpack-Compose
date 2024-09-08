package com.katorabian.terminal.presentation.screen.terminal

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import com.katorabian.terminal.data.dto.BarDto
import com.katorabian.terminal.presentation.TerminalState
import com.katorabian.terminal.presentation.rememberTerminalState
import kotlin.math.roundToInt

private const val MIN_VISIBLE_BARS_COUNT = 20

@Composable
fun TerminalGraphic(
    bars: List<BarDto>
) {
    var terminalState by rememberTerminalState(bars = bars)

    val transformableState = TransformableState { zoomChange, panChange, _ ->
        val visibleBarsCount = (terminalState.visibleBarsCount / zoomChange).roundToInt()
            .coerceIn(MIN_VISIBLE_BARS_COUNT, bars.size)

        val scrolledBy = (terminalState.scrolledBy + panChange.x)
            .coerceAtLeast(0F)
            .coerceAtMost(terminalState.barWidth * bars.count() - terminalState.terminalWidth)

        terminalState = terminalState.copy(
            visibleBarsCount = visibleBarsCount,
            scrolledBy = scrolledBy
        )
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .transformable(transformableState)
            .onSizeChanged {
                terminalState = terminalState.copy(
                    terminalWidth = it.width.toFloat()
                )
            }
    ) {
        val max = terminalState.visibleBars.maxOf { it.high }
        val min = terminalState.visibleBars.minOf { it.low }
        val difference = max - min
        val pxPerPoint = size.height / difference
        translate(left = terminalState.scrolledBy) {
            bars.forEachIndexed { index, bar ->
                val offsetX = size.width - index * terminalState.barWidth
                drawLine(
                    color = Color.White,
                    start = Offset(offsetX, size.height - ((bar.low - min) * pxPerPoint)),
                    end = Offset(offsetX, size.height - ((bar.high - min) * pxPerPoint)),
                    strokeWidth = 1F.toDp().toPx()
                )
                drawLine(
                    color = bar.getColor(),
                    start = Offset(offsetX, size.height - ((bar.open - min) * pxPerPoint)),
                    end = Offset(offsetX, size.height - ((bar.close - min) * pxPerPoint)),
                    strokeWidth = terminalState.barWidth / 2
                )
            }
        }
    }
}

fun BarDto.getColor() = if (open < close) Color.Green else Color.Red


@Preview
@Composable
private fun TerminalGraphicPreview() {
    TerminalGraphic(bars = emptyList())
}