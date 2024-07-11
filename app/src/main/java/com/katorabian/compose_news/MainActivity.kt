package com.katorabian.compose_news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.katorabian.compose_news.ui.theme.ComposeNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserInfo(name = "Alex", age = 20)
        }
    }
}

@Composable
fun UserInfo(name: String, age: Int) {
    Column {
        repeat(10) {
            Text("Hello, $name! You are $age years old")
        }
    }
}

@Preview
@Composable
fun UserInfoPreview() {
    UserInfo(name = "John", age = 26)
}