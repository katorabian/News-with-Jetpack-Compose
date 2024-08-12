package com.katorabian.compose_news.data.repository

import android.app.Application
import com.katorabian.compose_news.data.network.VkApiFactory
import com.katorabian.compose_news.domain.mapper.NewsFeedMapper
import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.PostCommentItem
import com.katorabian.compose_news.domain.model.StatisticItem
import com.katorabian.compose_news.domain.model.StatisticType
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class CommentsRepository(application: Application) {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val vkApi = VkApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _comments = mutableListOf<PostCommentItem>()
    val comments: List<PostCommentItem>
        get() = _comments.toList()

    private var nextFrom: String? = null

    private fun getAccessToken(): String {
        return token?.accessToken
            ?: throw IllegalStateException("Token is null")
    }

    suspend fun getComments(feedPost: FeedPostItem) {
        val response = vkApi.getComments(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        ).response
        val comments = mapper.mapResponseToComments(response)
        _comments.addAll(comments)
    }
}