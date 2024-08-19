package com.katorabian.practice.animations.animationsAsState

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.katorabian.practice.ui.theme.Pink80

@Composable
fun Test() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(
                state = rememberScrollState(),
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var isIncreased by remember { mutableStateOf(true) }
        val size by animateDpAsState(targetValue = if (isIncreased) 200.dp else 100.dp)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                isIncreased = !isIncreased
            }
        ) {
            Text(
                text = "Animate size",
            )
        }
        AnimatedContainer(
            text = "Size",
            size = size
        )
        /* ------------------------- */
        var isRect by remember { mutableStateOf(true) }
        val radiusPercent by animateIntAsState(targetValue = if (isRect) 4 else 100)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                isRect = !isRect
            }
        ) {
            Text(
                text = "Animate shape",
            )
        }
        AnimatedContainer(
            text = "Shape",
            radiusPercent = radiusPercent
        )
        /* ------------------------- */
        var hasBlackBorder by remember { mutableStateOf(false) }
        val borderWidth by animateDpAsState(targetValue = if (hasBlackBorder) 8.dp else 0.dp)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                hasBlackBorder = !hasBlackBorder
            }
        ) {
            Text(
                text = "Animate border",
            )
        }
        AnimatedContainer(
            text = "Border",
            borderWidth = borderWidth
        )
        /* ------------------------- */
        var isBlue by remember { mutableStateOf(true) }
        val contentColor by animateColorAsState(
            targetValue = if (isBlue) Color.Blue else Color.Magenta
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                isBlue = !isBlue
            }
        ) {
            Text(
                text = "Animate color",
            )
        }
        AnimatedContainer(
            text = "Color",
            contentColor = contentColor
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        ) {
            Text(
                text = "Animate visibility",
            )
        }
        AnimatedContainer(
            text = "Visibility"
        )
    }
}

@Composable
private fun AnimatedContainer(
    text: String,
    size: Dp = 200.dp,
    radiusPercent: Int = 4,
    borderWidth: Dp = 0.dp,
    contentColor: Color = Color.Blue
) {
    val shape = RoundedCornerShape(radiusPercent)
    Box(
        modifier = Modifier
            .clip(shape)
            .border(
                width = borderWidth,
                color = Color.Black,
                shape = shape
            )
            .background(contentColor)
            .size(size),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White
        )
    }
}
