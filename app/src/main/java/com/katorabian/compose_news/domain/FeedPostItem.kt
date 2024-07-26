package com.katorabian.compose_news.domain

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.katorabian.compose_news.R
import com.katorabian.compose_news.domain.annotation.TempClass
import kotlinx.parcelize.Parcelize

@TempClass("Until REST provide")
@Parcelize
data class FeedPostItem(
    val communityName: String = "/dev/null",
    val publicationDate: String = "14:00",
    val avatarResId: Int = R.drawable.post_comunity_thumbnail,
    val contentText: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    @DrawableRes val contentImageResId: Int = R.drawable.post_content_image,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(StatisticType.VIEWS, 966),
        StatisticItem(StatisticType.SHARES, 7),
        StatisticItem(StatisticType.COMMENTS, 8),
        StatisticItem(StatisticType.LIKES, 27)
    )
): Parcelable