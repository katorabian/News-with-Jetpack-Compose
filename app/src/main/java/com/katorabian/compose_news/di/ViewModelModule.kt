package com.katorabian.compose_news.di

import androidx.lifecycle.ViewModel
import com.katorabian.compose_news.presentation.screen.comments.CommentsViewModel
import com.katorabian.compose_news.presentation.screen.main.AuthViewModel
import com.katorabian.compose_news.presentation.screen.news.NewsFeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    @Binds
    fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    @Binds
    fun bindNewsFeedViewModel(viewModel: NewsFeedViewModel): ViewModel

}