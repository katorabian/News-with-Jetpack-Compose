package com.katorabian.compose_news.domain.model

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.navigation.NavType
import com.google.gson.Gson
import com.katorabian.compose_news.R
import com.katorabian.compose_news.domain.annotation.Temp
import kotlinx.parcelize.Parcelize

@Temp("Until REST provide")
@Parcelize
data class FeedPostItem(
    val id: Int,
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
): Parcelable {
    companion object {
        val NavigationType: NavType<FeedPostItem> = object : NavType<FeedPostItem>(
            isNullableAllowed = false
        ) {
            override fun get(bundle: Bundle, key: String): FeedPostItem? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): FeedPostItem {
                return Gson().fromJson(value, FeedPostItem::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: FeedPostItem) {
                bundle.putParcelable(key, value)
            }
        }
    }
}