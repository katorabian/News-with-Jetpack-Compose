package com.katorabian.compose_news.data.network

import com.katorabian.compose_news.data.model.CommentsDto
import com.katorabian.compose_news.data.model.CommentsResponseDto
import com.katorabian.compose_news.data.model.LikesCountResponseDto
import com.katorabian.compose_news.data.model.NewsFeedResponseDto
import com.katorabian.compose_news.data.model.VkBaseResponseDto
import com.katorabian.compose_news.domain.model.FeedPostItem
import retrofit2.http.GET
import retrofit2.http.Query

interface VkApi {
    private companion object {
        const val VK_API_VER = "5.199"
    }

    @GET("./newsfeed.getRecommended?v=$VK_API_VER")
    suspend fun loadRecommendation(
        @Query("access_token") token: String
    ): NewsFeedResponseDto

    @GET("./newsfeed.getRecommended?v=$VK_API_VER")
    suspend fun loadRecommendation(
        @Query("access_token") token: String,
        @Query("start_from") startFrom: String
    ): NewsFeedResponseDto

    @GET("./likes.add?v=$VK_API_VER&type=post")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto

    @GET("./likes.delete?v=$VK_API_VER&type=post")
    suspend fun deleteLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ): LikesCountResponseDto

    @GET("./newsfeed.ignoreItem?v=$VK_API_VER&type=wall")
    suspend fun ignorePost(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    )

    @GET("./wall.getComments?v=$VK_API_VER&extended=1&fields=photo_100")
    suspend fun getComments(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("post_id") postId: Long
    ): VkBaseResponseDto<CommentsResponseDto>

}