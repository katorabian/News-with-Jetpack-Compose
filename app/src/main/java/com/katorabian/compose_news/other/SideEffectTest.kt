package com.katorabian.compose_news.other

import android.os.Handler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.os.postDelayed

@Composable
fun SideEffectText(number: MyNumber) {
    Column {
        LazyColumn {
            repeat(5) {
                item {
                    Text(text = "Number: ${number.a}")
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Handler().postDelayed(3_000) {
            number.a = 5
        }
        LazyColumn {
            repeat(5) {
                item {
                    Text(text = "Number: ${number.a}")
                }
            }
        }
    }
}

data class MyNumber(var a: Int)