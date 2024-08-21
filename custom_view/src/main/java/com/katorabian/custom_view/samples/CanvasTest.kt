package com.katorabian.custom_view.samples

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun CanvasTest() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Cyan,
                        Color.Magenta,
                    ),
                    start = Offset(0F, 100.dp.toPx()),
                    end = Offset(200.dp.toPx(), 200.dp.toPx()),
                    tileMode = TileMode.Mirror
                ),
            )
    ) {
        drawPath(
            path = Path().apply {
                moveTo(center.x, 100.dp.toPx())
                lineTo(center.x + 25.dp.toPx(), 150.dp.toPx())
                lineTo(center.x + 75.dp.toPx(), 150.dp.toPx())
                lineTo(center.x + 40.dp.toPx(), 180.dp.toPx())
                lineTo(center.x + 65.dp.toPx(), 240.dp.toPx())
                lineTo(center.x, 205.dp.toPx())

                lineTo(center.x - 65.dp.toPx(), 240.dp.toPx())
                lineTo(center.x - 40.dp.toPx(), 180.dp.toPx())
                lineTo(center.x - 75.dp.toPx(), 150.dp.toPx())
                lineTo(center.x - 25.dp.toPx(), 150.dp.toPx())
                lineTo(center.x, 100.dp.toPx())
            },
            color = Color.White,
            style = Fill
        )
    }
}

@Composable
private fun Dp.toPx() = with(LocalDensity.current) {
    this@toPx.toPx()
}