package com.katorabian.compose_news.di

import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.presentation.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(
    modules = [
        CommentsViewModelModule::class
    ]
)
interface CommentsScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance feedPost: FeedPostItem
        ): CommentsScreenComponent

    }
}