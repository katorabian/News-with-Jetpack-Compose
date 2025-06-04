package com.katorabian.terminal.presentation.screen.terminal

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.katorabian.terminal.data.dto.BarDto
import com.katorabian.terminal.presentation.TerminalState
import com.katorabian.terminal.presentation.rememberTerminalState
import kotlin.math.roundToInt

private const val MIN_VISIBLE_BARS_COUNT = 20

@Composable
fun Terminal(
    modifier: Modifier = Modifier
) {
    val viewModel: TerminalViewModel = viewModel()
    val screenState = viewModel.state.collectAsState()

    when (val currState = screenState.value) {
        is TerminalScreenState.Content -> {
            val bars = currState.barList
            val terminalState = rememberTerminalState(bars = bars)

            Chart(
                modifier = modifier,
                terminalState = terminalState,
                onTerminalStateChange =  { newTerminalState ->
                    terminalState.value = newTerminalState
                }
            )

            bars.firstOrNull()?.let {
                Prices(
                    modifier = modifier,
                    terminalState = terminalState,
                    lastPrice = it.close
                )
            }
        }

        is TerminalScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.LightGray
                )
            }
        }

        is TerminalScreenState.Initial -> { /*do nothing*/ }
    }
}

@Composable
private fun Chart(
    modifier: Modifier = Modifier,
    terminalState: State<TerminalState>,
    onTerminalStateChange: (TerminalState) -> Unit
) {
    val currentState = terminalState.value
    val bars = currentState.barList
    val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
        val visibleBarsCount = (currentState.visibleBarsCount / zoomChange).roundToInt()
            .coerceIn(MIN_VISIBLE_BARS_COUNT, bars.size)

        val scrolledBy = (currentState.scrolledBy + panChange.x)
            .coerceAtLeast(0F)
            .coerceAtMost(currentState.barWidth * bars.count() - currentState.terminalWidth)

        onTerminalStateChange(
            currentState.copy(
                visibleBarsCount = visibleBarsCount,
                scrolledBy = scrolledBy
            )
        )
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .clipToBounds()
            .padding(
                top = 32.dp,
                bottom = 32.dp,
                end = 40.dp
            )
            .transformable(transformableState)
            .onSizeChanged {
                onTerminalStateChange(
                    currentState.copy(
                        terminalWidth = it.width.toFloat(),
                        terminalHeight = it.height.toFloat()
                    )
                )
            }
    ) {
        val min = currentState.min
        val pxPerPoint = currentState.pxPerPoint
        translate(left = currentState.scrolledBy) {
            bars.forEachIndexed { index, bar ->
                val offsetX = size.width - index * currentState.barWidth
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
                    strokeWidth = currentState.barWidth / 2
                )
            }
        }
    }
}

fun BarDto.getColor() = if (open < close) Color.Green else Color.Red

@Composable
private fun Prices(
    modifier: Modifier = Modifier,
    terminalState: State<TerminalState>,
    lastPrice: Float
) {
    val currentState = terminalState.value
    val textMeasurer = rememberTextMeasurer()

    val min = currentState.min
    val max = currentState.max
    val pxPerPoint = currentState.pxPerPoint

    Canvas(
        modifier
            .fillMaxSize()
            .clipToBounds()
            .padding(vertical = 32.dp)
    ) {
        drawPrices(
            max = max,
            min = min,
            lastPrice = lastPrice,
            pxPerPoint = pxPerPoint,
            textMeasurer = textMeasurer
        )
    }
}

private fun DrawScope.drawPrices(
    max: Float,
    min: Float,
    lastPrice: Float,
    pxPerPoint: Float,
    textMeasurer: TextMeasurer
) {
    // max price
    val maxPriceOffsetY = 0F // y = size.height - ((max - min) * pxPerPoint)
    drawDashLine(
        start = Offset(0F, maxPriceOffsetY),
        end = Offset(size.width, maxPriceOffsetY)
    )
    drawTextPrice(
        textMeasurer = textMeasurer,
        price = max,
        offsetY = maxPriceOffsetY
    )

    // last price
    val lastPriceOffsetY = size.height - ((lastPrice - min) * pxPerPoint)
    drawDashLine(
        start = Offset(0F, lastPriceOffsetY),
        end = Offset(size.width, lastPriceOffsetY),
    )
    drawTextPrice(
        textMeasurer = textMeasurer,
        price = lastPrice,
        offsetY = lastPriceOffsetY
    )

    // min price
    val minPriceOffsetY = size.height // y = size.height - ((min - min) * pxPerPoint)
    drawDashLine(
        start = Offset(0F, minPriceOffsetY),
        end = Offset(size.width, minPriceOffsetY),
    )
    drawTextPrice(
        textMeasurer = textMeasurer,
        price = min,
        offsetY = minPriceOffsetY
    )
}

private fun DrawScope.drawTextPrice(
    textMeasurer: TextMeasurer,
    price: Float,
    offsetY: Float
) {

    val textLayoutResult = textMeasurer.measure(
        text = price.toString(),
        style = TextStyle(
            color = Color.White,
            fontSize = 12.sp
        )
    )
    drawText(
        textLayoutResult = textLayoutResult,
        topLeft = Offset(size.width - textLayoutResult.size.width - 4.dp.toPx(), offsetY)
    )
}

private fun DrawScope.drawDashLine(
    color: Color = Color.White,
    start: Offset,
    end: Offset,
    strokeWidth: Float = 1.dp.toPx()
) {
    drawLine(
        color = color,
        start = start,
        end = end,
        strokeWidth = strokeWidth,
        pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(
                4.dp.toPx(), 4.dp.toPx(),
            )
        )
    )
}