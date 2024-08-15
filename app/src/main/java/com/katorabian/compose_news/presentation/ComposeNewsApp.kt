package com.katorabian.compose_news.presentation

import android.app.Application
import com.katorabian.compose_news.di.ApplicationComponent

class ComposeNewsApp: Application() {

    val component: ApplicationComponent by lazy {
        TODO("DaggerApplicationComponent")
    }
}