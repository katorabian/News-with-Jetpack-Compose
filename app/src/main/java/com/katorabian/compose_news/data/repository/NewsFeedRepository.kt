package com.katorabian.compose_news.data.repository

import android.app.Application
import com.katorabian.compose_news.data.network.ApiFactory
import com.katorabian.compose_news.domain.mapper.NewsFeedMapper
import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.StatisticItem
import com.katorabian.compose_news.domain.model.StatisticType
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class NewsFeedRepository(application: Application) {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPostItem>()
    val feedPosts: List<FeedPostItem> get() = _feedPosts.toList()

    suspend fun loadRecommendations(): List<FeedPostItem> {
        val response = apiService.loadRecommendation(getAccessToken())
        val posts = mapper.mapResponseToPosts(response)
        _feedPosts.addAll(posts)
        return posts
    }

    private fun getAccessToken(): String {
        return token?.accessToken
            ?: throw IllegalStateException("Token is null")
    }

    suspend fun addLike(feedPost: FeedPostItem) {
        val response = apiService.addLike(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )

        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(
                StatisticItem(
                    type = StatisticType.LIKES,
                    count = newLikesCount
                )
            )
        }
        val newPost = feedPost.copy(
            statistics = newStatistics,
            isLiked = true
        )

        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
    }
}