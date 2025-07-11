package com.katorabian.compose_news.data.mapper

import com.katorabian.compose_news.data.model.CommentsContentDto
import com.katorabian.compose_news.data.model.GroupShortDto
import com.katorabian.compose_news.data.model.NewsFeedContentDto
import com.katorabian.compose_news.data.model.PostDto
import com.katorabian.compose_news.data.model.ProfileShortDto
import com.katorabian.compose_news.data.model.VkBaseResponseDto
import com.katorabian.compose_news.domain.annotation.DateTimeFormatting
import com.katorabian.compose_news.common.constant.ZERO_INT
import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.PostCommentItem
import com.katorabian.compose_news.domain.model.StatisticItem
import com.katorabian.compose_news.domain.model.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.math.absoluteValue

class NewsFeedMapper @Inject constructor() {

    fun mapResponseToPosts(responseDto: VkBaseResponseDto<NewsFeedContentDto>): List<FeedPostItem> {
        val result = mutableListOf<FeedPostItem>()

        val posts = responseDto.generic.posts
        val groups = responseDto.generic.groups

        for (post in posts) {
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: continue
            val feedPost = FeedPostItem(
                id = post.id,
                communityId = post.communityId,
                communityName = group.name,
                publicationDate = mapTimestampToDate(post.date),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.getFirstPhotoMaxQuality(),
                statistics = post.extractStatistics(),
                isLiked = post.likes.userLikes > ZERO_INT
            )
            result.add(feedPost)
        }

        return result
    }

    fun mapResponseToComments(responseDto: CommentsContentDto): List<PostCommentItem> {
        val comments = mutableListOf<PostCommentItem>()
        for (comment in responseDto.comments) {
            val (authorName, authorAvatar) = responseDto.let {
                it.profiles.find { it.id == comment.authorId }?.getNameAndAvatar()?:
                it.communities.find { it.id == comment.authorId.absoluteValue }?.getNameAndAvatar()
            }?: continue

            val postComment = PostCommentItem(
                id = comment.id,
                authorName = authorName,
                authorAvatarUrl = authorAvatar,
                commentText = comment.text.takeIf { it.isNotBlank() }?: continue, // hide non text comments
                publicationDate = mapTimestampToDate(comment.date)
            )
            comments.add(postComment)
        }
        return comments
    }

    private fun ProfileShortDto.getFullName() = "$firstName $secondName"
    private fun ProfileShortDto.getNameAndAvatar() = getFullName() to avatarUrl
    private fun GroupShortDto.getNameAndAvatar() = name to avatar

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

    @DateTimeFormatting
    private fun mapTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp * 1000)
        val dateFormat = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
        return dateFormat.format(date)
    }

    companion object {
        private const val DATE_PATTERN = "d MMMM yyyy, hh:mm"

    }
}