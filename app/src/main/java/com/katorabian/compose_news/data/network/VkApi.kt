package com.katorabian.compose_news.data.network

import com.katorabian.compose_news.data.model.LikesCountResponseDto
import com.katorabian.compose_news.data.model.NewsFeedResponseDto
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
}