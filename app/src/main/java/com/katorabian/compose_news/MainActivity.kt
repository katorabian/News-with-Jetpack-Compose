package com.katorabian.compose_news

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
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
            LazyColumn {
                item {
                    Text(
                        text = "Hello user!",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                items(10) {
                    Log.d("LAZY_LIST_TEST", "Card: Part 1, Number: $it")
                    InstagramProfileCard(viewModel)
                }
                item {
                    Log.d("LAZY_LIST_TEST", "Image")
                    Image(
                        painter = painterResource(id = R.drawable.post_comunity_thumbnail),
                        contentDescription = null
                    )
                }
                items(200) {
                    Log.d("LAZY_LIST_TEST", "Card: Part 2, Number: $it")
                    InstagramProfileCard(viewModel)
                }
            }
        }
    }
}