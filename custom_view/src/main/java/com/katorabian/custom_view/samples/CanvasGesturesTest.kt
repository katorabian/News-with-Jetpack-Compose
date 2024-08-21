package com.katorabian.custom_view.samples

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Preview
@Composable
fun CanvasGesturesTest() {
    val circles = rememberSaveable {
        mutableStateOf<List<Offset>>(emptyList())
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(key1 = Unit) {
                detectTapGestures {
                    Log.d("CanvasGesturesTest", it.toString())
                    circles.value += it
                }
            }
    ) {
        circles.value.forEachIndexed { index, offset ->
            drawCircle(
                color = Color(
                    Random.nextInt(255),
                    Random.nextInt(255),
                    Random.nextInt(255),
                    Random.nextInt(255)
                ),
                radius = index.dp.toPx(),
                center = offset
            )
        }
    }
}