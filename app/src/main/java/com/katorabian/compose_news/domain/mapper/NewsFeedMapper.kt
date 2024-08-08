package com.katorabian.compose_news.domain.mapper

import com.katorabian.compose_news.data.model.NewsFeedResponseDto
import com.katorabian.compose_news.data.model.PostDto
import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.StatisticItem
import com.katorabian.compose_news.domain.model.StatisticType
import kotlin.math.absoluteValue

class NewsFeedMapper {

    fun mapResponseToPosts(responseDto: NewsFeedResponseDto): List<FeedPostItem> {
        val result = mutableListOf<FeedPostItem>()

        val posts = responseDto.newsFeedContentDto.posts
        val groups = responseDto.newsFeedContentDto.groups

        for (post in posts) {
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: continue
            val feedPost = FeedPostItem(
                id = post.id,
                communityName = group.name,
                publicationDate = post.date.toString(),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.getFirstPhotoMaxQuality(),
                statistics = post.extractStatistics()
            )
            result.add(feedPost)
        }

        return result
    }

    private fun PostDto.extractStatistics(): List<StatisticItem> {
        return listOf(
            StatisticItem(type = StatisticType.LIKES, count = likes.count),
            StatisticItem(type = StatisticType.VIEWS, count = views.count),
            StatisticItem(type = StatisticType.SHARES, count = reposts.count),
            StatisticItem(type = StatisticType.COMMENTS, count = comments.count)
        )
    }

    private fun PostDto.getFirstPhotoMaxQuality(): String? {
        val firstPhoto = attachments?.firstOrNull()
        val maxQuality = firstPhoto?.photo?.photoUrls?.lastOrNull()
        return maxQuality?.url
    }
}