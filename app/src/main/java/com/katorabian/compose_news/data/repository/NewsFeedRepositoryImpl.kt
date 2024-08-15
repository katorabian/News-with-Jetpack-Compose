package com.katorabian.compose_news.data.repository

import android.app.Application
import com.katorabian.compose_news.common.constant.FLOW_RETRY_TIMEOUT_MILLIS
import com.katorabian.compose_news.common.extensions.mergeWith
import com.katorabian.compose_news.data.network.VkApiFactory
import com.katorabian.compose_news.domain.mapper.NewsFeedMapper
import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.StatisticItem
import com.katorabian.compose_news.domain.model.StatisticType
import com.katorabian.compose_news.domain.repository.NewsFeedRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

class NewsFeedRepositoryImpl(application: Application): NewsFeedRepository {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<FeedPostItem>>()
    private val loadedListFlow = flow {
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom

            if (startFrom == null && feedPosts.isNotEmpty()) { //end of feed
                emit(feedPosts)
                return@collect
            }

            val response = if (startFrom == null) {
                vkApi.loadRecommendation(getAccessToken())
            } else {
                vkApi.loadRecommendation(getAccessToken(), startFrom)
            }
            nextFrom = response.generic.nextFrom
            val posts = mapper.mapResponseToPosts(response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
    }.retry {
        delay(FLOW_RETRY_TIMEOUT_MILLIS)
        true
    }


    private val vkApi = VkApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPostItem>()
    private val feedPosts: List<FeedPostItem>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    private val recommendations: StateFlow<List<FeedPostItem>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    override fun getRecommendations(): StateFlow<List<FeedPostItem>> = recommendations

    override suspend fun loadNextData() {
        nextDataNeededEvents.emit(Unit)
    }

    private fun getAccessToken(): String {
        return token?.accessToken
            ?: throw IllegalStateException("Token is null")
    }

    override suspend fun changeLikeStatus(feedPost: FeedPostItem) {
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

        val newLikesCount = response.generic.count
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
        refreshedListFlow.emit(feedPosts)
    }

    override suspend fun deletePost(feedPost: FeedPostItem) {
        vkApi.ignorePost(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.removeIf { it.id == feedPost.id }
        refreshedListFlow.emit(feedPosts)
    }
}