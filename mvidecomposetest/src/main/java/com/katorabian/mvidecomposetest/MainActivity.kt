package com.katorabian.mvidecomposetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.katorabian.mvidecomposetest.presentation.DefaultRootComponent
import com.katorabian.mvidecomposetest.ui.content.RootContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootContent(component = DefaultRootComponent(defaultComponentContext()))
        }
    }
}