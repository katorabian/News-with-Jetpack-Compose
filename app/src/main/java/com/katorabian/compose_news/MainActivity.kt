package com.katorabian.compose_news

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.SideEffect
import com.katorabian.compose_news.other.ActivityResultTest
import com.katorabian.compose_news.other.InstagramViewModel
import com.katorabian.compose_news.other.MyNumber
import com.katorabian.compose_news.other.SideEffectText
import com.katorabian.compose_news.presentation.layout.MainScreen
import com.katorabian.compose_news.presentation.theme.ComposeNewsTheme
import com.katorabian.compose_news.presentation.viewModel.NewsFeedViewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.VK.getVKAuthActivityResultContract
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {
    private val instagramViewModel by viewModels<InstagramViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*
        setContent {
            ComposeNewsTheme {
                InstagramScreen(viewModel = instagramViewModel)
            }
        }
*/

        setContent {
            ComposeNewsTheme {
                val launcher = rememberLauncherForActivityResult(
                    contract = getVKAuthActivityResultContract()
                ) {
                    when (it) {
                        is VKAuthenticationResult.Success -> {
                            Log.d("MainActivity", "Success auth")
                        }
                        is VKAuthenticationResult.Failed -> {
                            Log.e("MainActivity", "Failed auth")
                        }
                    }
                }
                SideEffect {
                    launcher.launch(listOf(VKScope.WALL))
                }
                MainScreen()
            }
        }
    }
}