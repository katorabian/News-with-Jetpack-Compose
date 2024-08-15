package com.katorabian.compose_news.di

import androidx.lifecycle.ViewModel
import com.katorabian.compose_news.presentation.screen.comments.CommentsViewModel
import com.katorabian.compose_news.presentation.screen.main.AuthViewModel
import com.katorabian.compose_news.presentation.screen.news.NewsFeedViewModel
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {

    @Binds
    fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

    @Binds
    fun bindNewsFeedViewModel(viewModel: NewsFeedViewModel): ViewModel

    @Binds
    fun bindCommentsViewModel(viewModel: CommentsViewModel): ViewModel
}