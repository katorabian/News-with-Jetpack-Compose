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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.katorabian.compose_news.other.ActivityResultTest
import com.katorabian.compose_news.other.InstagramViewModel
import com.katorabian.compose_news.other.MyNumber
import com.katorabian.compose_news.other.SideEffectText
import com.katorabian.compose_news.presentation.layout.LoginScreen
import com.katorabian.compose_news.presentation.layout.MainScreen
import com.katorabian.compose_news.presentation.model.AuthState
import com.katorabian.compose_news.presentation.theme.ComposeNewsTheme
import com.katorabian.compose_news.presentation.viewModel.LoginViewModel
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
                val viewModel: LoginViewModel = viewModel()
                val authState = viewModel.authState.observeAsState(AuthState.Initial)

                val launcher = rememberLauncherForActivityResult(
                    contract = getVKAuthActivityResultContract(),
                    onResult = viewModel::handleAuthResult
                )

                when (authState.value) {
                    is AuthState.Authorized -> {
                        MainScreen()
                    }
                    is AuthState.Unauthorized -> {
                        LoginScreen {
                            launcher.launch(listOf(VKScope.WALL))
                        }
                    }
                    else -> {
                        /* do nothing */
                    }
                }
            }
        }
    }
}