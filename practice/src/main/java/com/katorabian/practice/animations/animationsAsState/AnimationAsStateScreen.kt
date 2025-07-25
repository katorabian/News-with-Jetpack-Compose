package com.katorabian.practice.animations.animationsAsState

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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
        val infiniteTransition = rememberInfiniteTransition()
        val size by infiniteTransition.animateFloat(
            initialValue = 200F,
            targetValue = 100F,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2000),
                repeatMode = RepeatMode.Reverse
            )
        )
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
            size = size.dp
        )
        /* ------------------------- */
        val rotateDegree by infiniteTransition.animateFloat(
            initialValue = 0F,
            targetValue = 360F,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Restart
            )
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        ) {
            Text(
                text = "Animate shape",
            )
        }
        AnimatedContainer(
            text = "Shape",
            rotateDegree = rotateDegree
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
        /* ------------------------- */
        var isTransparent by remember { mutableStateOf(false) }
        val alpha by animateFloatAsState(targetValue = if (isTransparent) 0F else 1F)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                isTransparent = !isTransparent
            }
        ) {
            Text(
                text = "Animate visibility",
            )
        }
        AnimatedContainer(
            text = "Visibility",
            alpha = alpha
        )
    }
}

@Composable
private fun AnimatedContainer(
    text: String,
    size: Dp = 200.dp,
    radiusPercent: Int = 4,
    borderWidth: Dp = 0.dp,
    contentColor: Color = Color.Blue,
    alpha: Float = 1F,
    rotateDegree: Float = 0F
) {
    val shape = RoundedCornerShape(radiusPercent)
    Box(
        modifier = Modifier
            .alpha(alpha)
            .rotate(rotateDegree)
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
