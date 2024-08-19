package com.katorabian.compose_news.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.katorabian.compose_news.di.ApplicationComponent
import com.katorabian.compose_news.di.DaggerApplicationComponent

class ComposeNewsApp: Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(
            context = this
        )
    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    Log.e("RECOMPOSITION_TAG", "getApplicationComponent")
    return (LocalContext.current.applicationContext as ComposeNewsApp).component
}