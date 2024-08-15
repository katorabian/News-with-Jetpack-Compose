package com.katorabian.compose_news.di

import android.content.Context
import com.katorabian.compose_news.data.network.VkApi
import com.katorabian.compose_news.data.network.VkApiFactory
import com.katorabian.compose_news.data.repository.AuthRepositoryImpl
import com.katorabian.compose_news.data.repository.CommentsRepositoryImpl
import com.katorabian.compose_news.data.repository.NewsFeedRepositoryImpl
import com.katorabian.compose_news.domain.repository.AuthRepository
import com.katorabian.compose_news.domain.repository.CommentsRepository
import com.katorabian.compose_news.domain.repository.NewsFeedRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @ApplicationScope
    @Binds
    fun bindNewsFeedRepository(impl: NewsFeedRepositoryImpl): NewsFeedRepository
    
    @Binds
    fun bindCommentsRepository(impl: CommentsRepositoryImpl): CommentsRepository


    companion object {
        @ApplicationScope
        @Provides
        fun provideApiService(): VkApi {
            return VkApiFactory.apiService
        }

        @ApplicationScope
        @Provides
        fun provideVkStorage(
            context: Context
        ): VKPreferencesKeyValueStorage {
            return VKPreferencesKeyValueStorage(context)
        }
    }
}