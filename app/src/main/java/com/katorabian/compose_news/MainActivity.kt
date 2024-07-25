package com.katorabian.compose_news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.katorabian.compose_news.ui.theme.ComposeNewsTheme
import com.katorabian.compose_news.ui.theme.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*
        setContent {
            val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
            ComposeNewsTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                ) {
                    InstagramProfileCard(viewModel)
                }
            }
        }
*/
        setContent {
            ComposeNewsTheme {
                MainScreen()
            }
        }
    }
}