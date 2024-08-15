package com.katorabian.compose_news.data.repository

import com.katorabian.compose_news.data.mapper.NewsFeedMapper
import com.katorabian.compose_news.data.network.VkApi
import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.PostCommentItem
import com.katorabian.compose_news.domain.repository.CommentsRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import javax.inject.Inject

class CommentsRepositoryImpl @Inject constructor(
    private val vkApi: VkApi,
    private val mapper: NewsFeedMapper,
    storage: VKPreferencesKeyValueStorage
): CommentsRepository {

    private val token = VKAccessToken.restore(storage)

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