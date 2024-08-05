package com.katorabian.compose_news.presentation.navigation

import android.net.Uri
import com.google.gson.Gson
import com.katorabian.compose_news.domain.model.FeedPostItem

sealed class Screen (val route: String) {

    data object Home: Screen(ROUTE_HOME)
    data object NewsFeed: Screen(ROUTE_NEWS_FEED)
    data object Comments: Screen(ROUTE_COMMENTS) {
        internal const val ROUTE_FOR_ARGS = "comments"

        fun getRouteWithArgs(feedPost: FeedPostItem): String {
            val feedPostJson = Gson().toJson(feedPost)
            return "$ROUTE_FOR_ARGS/${feedPostJson.encode()}" // "comments/post_item_gson"
        }
    }

    data object Favourite: Screen(ROUTE_FAVOURITE)
    data object Profile: Screen(ROUTE_PROFILE)

    companion object {
        const val KEY_FEED_POST = "feed_post"

        const val ROUTE_HOME = "home"
        const val ROUTE_COMMENTS = "${Comments.ROUTE_FOR_ARGS}/{$KEY_FEED_POST}" // "comments/{key_feed_post}
        const val ROUTE_NEWS_FEED = "news_feed"

        const val ROUTE_FAVOURITE = "favourite"
        const val ROUTE_PROFILE = "profile"
    }
}

fun String.encode(): String = Uri.encode(this)