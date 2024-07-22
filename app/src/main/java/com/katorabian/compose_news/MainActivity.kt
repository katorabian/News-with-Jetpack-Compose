package com.katorabian.compose_news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.katorabian.compose_news.ui.theme.ComposeNewsTheme
import com.katorabian.compose_news.ui.theme.PostCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNewsTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(8.dp)
                ) {
                    PostCard()
                }
            }
        }
    }
}

@Preview
@Composable
fun Tests() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
//        Example1()
//        Example2()
        Example3()
    }
}

@Preview
@Composable
fun Example1() {
    OutlinedButton(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(6.dp),
    ) {
        Text(text = "Hello World")
    }
}

@Preview
@Composable
fun Example2() {
    TextField(
        value = "Value",
        onValueChange = {},
        label = { Text(text = "Label") },
        placeholder = { Text(text = "placeholder") }
    )
}

@Preview
@Composable
fun Example3() {
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        confirmButton = { Text(text = "Yes") },
        dismissButton = { Text(text = "No")},
        title = {
            Text(
                text = "Are you sure?",
                fontSize = 20.sp
            )
        },
        text = { Text(text = "Do you want to delete this file?") },
        shape = RoundedCornerShape(8.dp)
    )
}
