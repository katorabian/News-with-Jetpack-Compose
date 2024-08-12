package com.katorabian.compose_news.data.repository

import android.app.Application
import com.katorabian.compose_news.data.network.VkApiFactory
import com.katorabian.compose_news.domain.mapper.NewsFeedMapper
import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.StatisticItem
import com.katorabian.compose_news.domain.model.StatisticType
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class NewsFeedRepository(application: Application) {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val vkApi = VkApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPostItem>()
    val feedPosts: List<FeedPostItem>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    suspend fun loadRecommendations(): List<FeedPostItem> {
        val startFrom = nextFrom

        if (startFrom == null && feedPosts.isNotEmpty())
            return feedPosts

        val response = if (startFrom == null) {
            vkApi.loadRecommendation(getAccessToken())
        } else {
            vkApi.loadRecommendation(getAccessToken(), startFrom)
        }
        nextFrom = response.newsFeedContentDto.nextFrom
        val posts = mapper.mapResponseToPosts(response)
        _feedPosts.addAll(posts)
        return feedPosts
    }

    private fun getAccessToken(): String {
        return token?.accessToken
            ?: throw IllegalStateException("Token is null")
    }

    suspend fun changeLikeStatus(feedPost: FeedPostItem) {
        val response = if (feedPost.isLiked) {
            vkApi.deleteLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        } else {
            vkApi.addLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
        }

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
            isLiked = !feedPost.isLiked
        )

        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
    }

    suspend fun deletePost(feedPost: FeedPostItem) {
        vkApi.ignorePost(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.removeIf { it.id == feedPost.id }
    }
}