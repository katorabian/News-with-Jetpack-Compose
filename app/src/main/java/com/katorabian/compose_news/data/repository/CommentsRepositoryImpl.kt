package com.katorabian.compose_news.data.repository

import android.app.Application
import com.katorabian.compose_news.data.network.VkApiFactory
import com.katorabian.compose_news.domain.mapper.NewsFeedMapper
import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.PostCommentItem
import com.katorabian.compose_news.domain.repository.CommentsRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class CommentsRepositoryImpl(application: Application): CommentsRepository {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val vkApi = VkApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private fun getAccessToken(): String {
        return token?.accessToken
            ?: throw IllegalStateException("Token is null")
    }

    override suspend fun getComments(feedPost: FeedPostItem): List<PostCommentItem> {
        val response = vkApi.getComments(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        ).generic
        val comments = mapper.mapResponseToComments(response)
        return comments
    }
}