package com.katorabian.compose_news.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.katorabian.compose_news.presentation.screen.main.LoginScreen
import com.katorabian.compose_news.presentation.screen.main.LoginViewModel
import com.katorabian.compose_news.presentation.screen.main.MainScreen
import com.katorabian.compose_news.presentation.screen.main.models.AuthState
import com.katorabian.compose_news.presentation.screen.samples.instagram.InstagramViewModel
import com.katorabian.compose_news.presentation.theme.ComposeNewsTheme
import com.vk.api.sdk.VK.getVKAuthActivityResultContract
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