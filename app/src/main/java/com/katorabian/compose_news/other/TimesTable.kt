package com.katorabian.compose_news.other

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun TimesTable() {
    Column(modifier = Modifier.fillMaxSize()) {
        for (column in 1 until 10) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            ) {
                for (row in 1 until 10) {
                    val value = column * row
                    val even = (column + row) % 2 == 0
                    val color = if (even) Color.Yellow else Color.White
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1F)
                            .border(width = 1.dp, color = Color.DarkGray)
                            .background(color = color),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = value.toString())
                    }
                }
            }
        }
    }
}