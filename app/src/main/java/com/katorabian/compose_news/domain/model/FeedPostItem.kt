package com.katorabian.compose_news.domain.model

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import com.katorabian.compose_news.domain.annotation.Temp
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Temp("Until REST provide")
@Parcelize
data class FeedPostItem(
    val id: String,
    val communityName: String,
    val publicationDate: String,
    val communityImageUrl: String,
    val contentText: String,
    val contentImageUrl: String?,
    val statistics: List<StatisticItem>,
    val isFavourite: Boolean = @Temp Random.nextBoolean()
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