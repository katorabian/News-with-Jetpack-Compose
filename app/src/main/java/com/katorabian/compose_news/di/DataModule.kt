package com.katorabian.compose_news.di

import com.katorabian.compose_news.data.repository.AuthRepositoryImpl
import com.katorabian.compose_news.data.repository.CommentsRepositoryImpl
import com.katorabian.compose_news.data.repository.NewsFeedRepositoryImpl
import com.katorabian.compose_news.domain.repository.AuthRepository
import com.katorabian.compose_news.domain.repository.CommentsRepository
import com.katorabian.compose_news.domain.repository.NewsFeedRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
    @Binds
    fun bindNewsFeedRepository(impl: NewsFeedRepositoryImpl): NewsFeedRepository
    @Binds
    fun bindCommentsRepository(impl: CommentsRepositoryImpl): CommentsRepository
}