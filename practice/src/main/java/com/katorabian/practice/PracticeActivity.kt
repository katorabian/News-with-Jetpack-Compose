package com.katorabian.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.katorabian.practice.instagram.InstagramScreen
import com.katorabian.practice.instagram.InstagramViewModel
import com.katorabian.practice.ui.theme.ComposeNewsTheme

class PracticeActivity : ComponentActivity() {
    private val instagramViewModel by viewModels<InstagramViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNewsTheme {
                InstagramScreen(viewModel = instagramViewModel)
            }
        }
    }
}