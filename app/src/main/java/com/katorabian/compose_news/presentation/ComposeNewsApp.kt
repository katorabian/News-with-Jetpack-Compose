package com.katorabian.compose_news.presentation

import android.app.Application
import com.katorabian.compose_news.common.constant.EMPTY_STRING
import com.katorabian.compose_news.common.constant.ZERO_LONG
import com.katorabian.compose_news.di.ApplicationComponent
import com.katorabian.compose_news.di.DaggerApplicationComponent
import com.katorabian.compose_news.domain.model.FeedPostItem

class ComposeNewsApp: Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(
            context = this
        )
    }
}