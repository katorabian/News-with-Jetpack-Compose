package com.katorabian.custom_view.samples

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun CanvasTest() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // "О"
        drawCircle(
            color = Color.Cyan,
            radius = 20.dp.toPx(),
            center = Offset(50.dp.toPx(), 50.dp.toPx()),
            style = Stroke(width = 1.dp.toPx())
        )

        // "Л"
        drawLine(
            color = Color.Magenta,
            start = Offset(96.dp.toPx(), 30.dp.toPx()),
            end = Offset(78.dp.toPx(), 70.dp.toPx()),
            strokeWidth = 1.dp.toPx()
        )
        drawLine(
            color = Color.Magenta,
            start = Offset(96.dp.toPx(), 30.dp.toPx()),
            end = Offset(114.dp.toPx(), 70.dp.toPx()),
            strokeWidth = 1.dp.toPx()
        )

        // "Е"
        drawLine(
            color = Color.Yellow,
            start = Offset(126.dp.toPx(), 30.dp.toPx()),
            end = Offset(126.dp.toPx(), 70.dp.toPx()),
            strokeWidth = 1.dp.toPx()
        )
        drawLine(
            color = Color.Yellow,
            start = Offset(126.dp.toPx(), 30.dp.toPx()),
            end = Offset(150.dp.toPx(), 30.dp.toPx()),
            strokeWidth = 1.dp.toPx()
        )
        drawLine(
            color = Color.Yellow,
            start = Offset(126.dp.toPx(), 50.dp.toPx()),
            end = Offset(150.dp.toPx(), 50.dp.toPx()),
            strokeWidth = 1.dp.toPx()
        )
        drawLine(
            color = Color.Yellow,
            start = Offset(126.dp.toPx(), 70.dp.toPx()),
            end = Offset(150.dp.toPx(), 70.dp.toPx()),
            strokeWidth = 1.dp.toPx()
        )

        // "Г"
        drawLine(
            color = Color.Red,
            start = Offset(164.dp.toPx(), 30.dp.toPx()),
            end = Offset(192.dp.toPx(), 30.dp.toPx()),
            strokeWidth = 1.dp.toPx()
        )
        drawLine(
            color = Color.Red,
            start = Offset(164.dp.toPx(), 30.dp.toPx()),
            end = Offset(164.dp.toPx(), 70.dp.toPx()),
            strokeWidth = 1.dp.toPx()
        )

    }
}