package com.katorabian.compose_news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.katorabian.compose_news.ui.theme.ComposeNewsTheme
import com.katorabian.compose_news.ui.theme.MainScreen

class MainActivity : ComponentActivity() {
    private val postViewModel by viewModels<PostViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*
        setContent {
            val viewModel = ViewModelProvider(this)[InstagramViewModel::class.java]
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
                MainScreen(postViewModel)
            }
        }
    }
}