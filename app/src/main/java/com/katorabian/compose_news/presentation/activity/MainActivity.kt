package com.katorabian.compose_news.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.katorabian.compose_news.domain.annotation.Temp
import com.katorabian.compose_news.presentation.screen.main.LoginScreen
import com.katorabian.compose_news.presentation.screen.main.AuthViewModel
import com.katorabian.compose_news.presentation.screen.main.MainScreen
import com.katorabian.compose_news.domain.model.AuthState
import com.katorabian.compose_news.presentation.ComposeNewsApp
import com.katorabian.compose_news.presentation.ViewModelFactory
import com.katorabian.compose_news.presentation.theme.ComposeNewsTheme
import com.vk.api.sdk.VK.getVKAuthActivityResultContract
import com.vk.api.sdk.auth.VKScope
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Temp
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as ComposeNewsApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            ComposeNewsTheme {
                val viewModel: AuthViewModel = viewModel()
                val authState = viewModel.authState.collectAsState(AuthState.Initial)

                val launcher = rememberLauncherForActivityResult(
                    contract = getVKAuthActivityResultContract(),
                    onResult = { viewModel.handleAuthResult() }
                )

                when (authState.value) {
                    is AuthState.Authorized -> {
                        MainScreen()
                    }
                    is AuthState.Unauthorized -> {
                        LoginScreen {
                            launcher.launch(
                                listOf(VKScope.WALL, VKScope.FRIENDS)
                            )
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