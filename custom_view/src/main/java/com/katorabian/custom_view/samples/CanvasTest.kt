package com.katorabian.custom_view.samples

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun CanvasTest() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        drawLine(
            color = Color.White,
            start = Offset(0F, 0F),
            end = Offset(size.width, size.height),
            strokeWidth = 1F
        )
        drawLine(
            color = Color.White,
            start = Offset(0F, size.height),
            end = Offset(size.width, 0F),
            strokeWidth = 1F
        )
        drawCircle(
            color = Color.White,
            radius = 100F,
            style = Stroke(width = 1F)
        )
    }
}