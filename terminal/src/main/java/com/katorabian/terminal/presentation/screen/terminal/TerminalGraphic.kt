package com.katorabian.terminal.presentation.screen.terminal

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.katorabian.terminal.data.dto.BarDto

@Composable
fun TerminalGraphic(
    bars: List<BarDto>
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val max = bars.maxOf { it.high }
        val min = bars.minOf { it.low }
        val difference = max - min
        val barWidth = size.width / bars.count()
        val pxPerPoint = size.height / difference
        bars.forEachIndexed { index, bar ->
            val offsetX = index * barWidth
            drawLine(
                color = Color.White,
                start = Offset(offsetX, size.height - ((bar.low - min) * pxPerPoint)),
                end = Offset(offsetX, size.height - ((bar.high - min) * pxPerPoint)),
                strokeWidth = 1F.toDp().toPx()
            )
        }
    }
}


@Preview
@Composable
private fun TerminalGraphicPreview() {
    TerminalGraphic(bars = emptyList())
}