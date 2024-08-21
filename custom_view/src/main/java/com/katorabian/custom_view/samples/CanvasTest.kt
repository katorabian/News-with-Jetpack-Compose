package com.katorabian.custom_view.samples

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
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
        fun Int.px() = dp.toPx()
        drawPath(
            path = Path().apply {
                moveTo(center.x, 100.px())
                lineTo(center.x + 25.px(), 150.px())
                lineTo(center.x + 75.px(), 150.px())
                lineTo(center.x + 40.px(), 180.px())
                lineTo(center.x + 65.px(), 240.px())
                lineTo(center.x, 205.px())

                lineTo(center.x - 65.px(), 240.px())
                lineTo(center.x - 40.px(), 180.px())
                lineTo(center.x - 75.px(), 150.px())
                lineTo(center.x - 25.px(), 150.px())
                lineTo(center.x, 100.px())
            },
            color = Color.White,
            style = Fill
        )
    }
}