package com.katorabian.compose_news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.katorabian.compose_news.other.InstagramScreen
import com.katorabian.compose_news.other.InstagramViewModel
import com.katorabian.compose_news.presentation.theme.ComposeNewsTheme
import com.katorabian.compose_news.presentation.viewModel.PostViewModel

class MainActivity : ComponentActivity() {
    private val postViewModel by viewModels<PostViewModel>()
    private val instagramViewModel by viewModels<InstagramViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNewsTheme {
                InstagramScreen(viewModel = instagramViewModel)
            }
        }
/*
        setContent {
            ComposeNewsTheme {
                MainScreen(postViewModel)
            }
        }
*/
    }
}