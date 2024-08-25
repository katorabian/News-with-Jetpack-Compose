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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import com.katorabian.terminal.data.dto.BarDto
import kotlin.math.roundToInt

private const val MIN_VISIBLE_BARS_COUNT = 20

@Composable
fun TerminalGraphic(
    bars: List<BarDto>
) {
    var visibleBarsCount by remember {
        mutableStateOf(100)
    }
    var scrolledBy by remember {
        mutableStateOf(0F)
    }

    val transformableState = TransformableState { zoomChange, panChange, _ ->
        val newCount = (visibleBarsCount / zoomChange).roundToInt()
        visibleBarsCount = newCount
            .coerceIn(MIN_VISIBLE_BARS_COUNT, bars.size)

        scrolledBy += panChange.x
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .transformable(transformableState)
    ) {
        val max = bars.maxOf { it.high }
        val min = bars.minOf { it.low }
        val difference = max - min
        val barWidth = size.width / visibleBarsCount
        val pxPerPoint = size.height / difference
        translate(left = scrolledBy) {
            bars.forEachIndexed { index, bar ->
                val offsetX = size.width - index * barWidth
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
                    strokeWidth = barWidth / 2
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