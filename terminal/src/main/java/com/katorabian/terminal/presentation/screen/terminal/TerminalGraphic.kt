package com.katorabian.terminal.presentation.screen.terminal

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.katorabian.terminal.data.dto.BarDto
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
            .padding(
                top = 32.dp,
                bottom = 32.dp
            )
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
        terminalState.visibleBars.firstOrNull()?.let {
            drawPrices(
                max = max,
                min = min,
                lastPrice = it.close,
                pxPerPoint = pxPerPoint
            )
        }
    }
}

fun BarDto.getColor() = if (open < close) Color.Green else Color.Red


private fun DrawScope.drawPrices(
    max: Float,
    min: Float,
    lastPrice: Float,
    pxPerPoint: Float
) {
    val dashEffect = PathEffect.dashPathEffect(
        intervals = floatArrayOf(
            4.dp.toPx(), 4.dp.toPx(),
        )
    )

    // max price
    drawLine(
        color = Color.White,
        start = Offset(0F, size.height - ((max - min) * pxPerPoint)),
        end = Offset(size.width, size.height - ((max - min) * pxPerPoint)),
        strokeWidth = 1.dp.toPx(),
        pathEffect = dashEffect

    )

    // last price
    drawLine(
        color = Color.White,
        start = Offset(0F, size.height - ((lastPrice - min) * pxPerPoint)),
        end = Offset(size.width, size.height - ((lastPrice - min) * pxPerPoint)),
        strokeWidth = 1.dp.toPx(),
        pathEffect = dashEffect
    )

    // min price
    drawLine(
        color = Color.White,
        start = Offset(0F, size.height - ((min - min) * pxPerPoint)),
        end = Offset(size.width, size.height - ((min - min) * pxPerPoint)),
        strokeWidth = 1.dp.toPx(),
        pathEffect = dashEffect
    )
}