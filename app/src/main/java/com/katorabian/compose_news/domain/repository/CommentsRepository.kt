package com.katorabian.compose_news.domain.repository

import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.PostCommentItem

interface CommentsRepository {

    suspend fun getComments(feedPost: FeedPostItem): List<PostCommentItem>
}