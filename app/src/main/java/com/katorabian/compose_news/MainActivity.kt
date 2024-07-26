package com.katorabian.compose_news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.katorabian.compose_news.ui.theme.ComposeNewsTheme
import com.katorabian.compose_news.ui.theme.InstagramProfileCard

class MainActivity : ComponentActivity() {
    private val postViewModel by viewModels<PostViewModel>()
    private val viewModel by viewModels<InstagramViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstagramTest(viewModel = viewModel)
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

@Composable
private fun InstagramTest(viewModel: InstagramViewModel) {
    ComposeNewsTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            val scrollState = rememberScrollState()
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                repeat(100) {
                    InstagramProfileCard(viewModel)
                }
            }
        }
    }
}