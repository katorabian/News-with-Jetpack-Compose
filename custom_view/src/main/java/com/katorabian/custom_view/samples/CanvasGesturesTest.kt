package com.katorabian.custom_view.samples

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun CanvasGesturesTest() {
    val points = rememberSaveable {
        mutableStateOf<List<Offset>>(emptyList())
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(key1 = Unit) {
                detectDragGestures { change, _ ->
                    val newPoints = change.historical.map { it.position }
                    points.value += newPoints
                    Log.d("CanvasGesturesTest",
                        newPoints
                            .lastOrNull()
                            .toString()
                    )
                }
            }
    ) {
        val path = Path()
        val brush = Brush.linearGradient(
            listOf(Color.Cyan, Color.Magenta, Color.Red)
        )

        points.value.forEachIndexed { index, offset ->
            if (index == 0) {
                path.moveTo(offset.x, offset.y)
            } else {
                path.lineTo(offset.x, offset.y)
            }
        }
        drawPath(
            path = path,
            brush = brush,
            style = Stroke(width = 10.dp.toPx())
        )
    }
}