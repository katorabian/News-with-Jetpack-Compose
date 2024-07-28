package com.katorabian.compose_news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.katorabian.compose_news.other.InstagramProfileCard
import com.katorabian.compose_news.other.InstagramViewModel
import com.katorabian.compose_news.presentation.theme.ComposeNewsTheme
import com.katorabian.compose_news.presentation.viewModel.PostViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val postViewModel by viewModels<PostViewModel>()
    private val instagramViewModel by viewModels<InstagramViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstagramTest(viewModel = instagramViewModel)
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
            val models = viewModel.models.observeAsState(emptyList())
            val scope = rememberCoroutineScope()
            val lazyListState = rememberLazyListState()
            LazyColumn(state = lazyListState) {
                items(models.value) { instagramItem ->
                    InstagramProfileCard(
                        instagramItem = instagramItem,
                        onFollowedButtonClickListener = viewModel::changeFollowedStatus
                    )
                }
            }

            FloatingActionButton(
                onClick = {
                    scope.launch {
                        lazyListState.scrollToItem(0, 0)
                    }
                }
            ) {}
        }
    }
}