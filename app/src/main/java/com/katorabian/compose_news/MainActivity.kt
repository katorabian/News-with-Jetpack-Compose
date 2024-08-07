package com.katorabian.compose_news

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.delay

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
                val someState = remember {
                    mutableStateOf(true)
                }

                Log.e("MainActivity", "Recomposition: ${someState.value}")
                val launcher = rememberLauncherForActivityResult(
                    contract = getVKAuthActivityResultContract()
                ) {
                    when (it) {
                        is VKAuthenticationResult.Success -> {
                            Log.e("MainActivity", "Success auth")
                        }
                        is VKAuthenticationResult.Failed -> {
                            Log.e("MainActivity", "Failed auth")
                        }
                    }
                }
                LaunchedEffect(key1 = someState.value) { //executes at first call or when key is changed
                    Log.e("MainActivity", "LaunchedEffect")
                    delay(100)
                }
                SideEffect {
                    Log.d("MainActivity", "SideEffect")
                }
                Button(onClick = { someState.value = !someState.value }) {
                    Text(text = "Update state")
                }
            }
        }
    }
}