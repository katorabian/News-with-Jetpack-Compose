package com.katorabian.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.katorabian.practice.animations.animationsContent.AnimateContent
import com.katorabian.practice.instagram.InstagramViewModel
import com.katorabian.practice.ui.theme.ComposeNewsTheme

class PracticeActivity : ComponentActivity() {
    private val instagramViewModel by viewModels<InstagramViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNewsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AnimateContent()
                }
//                InstagramScreen(viewModel = instagramViewModel)
            }
        }
    }
}