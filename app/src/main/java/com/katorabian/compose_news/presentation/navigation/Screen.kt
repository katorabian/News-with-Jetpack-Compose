package com.katorabian.compose_news.presentation.navigation

import com.katorabian.compose_news.domain.model.FeedPostItem

sealed class Screen (val route: String) {

    data object Home: Screen(ROUTE_HOME)
    data object NewsFeed: Screen(ROUTE_NEWS_FEED)
    data object Comments: Screen(ROUTE_COMMENTS) {
        internal const val ROUTE_FOR_ARGS = "comments"

        fun getRouteWithArgs(feedPost: FeedPostItem): String {
            return "$ROUTE_FOR_ARGS/${feedPost.id}/${feedPost.contentText}" // "comments/15/lorem ipsum"
        }
    }

    data object Favourite: Screen(ROUTE_FAVOURITE)
    data object Profile: Screen(ROUTE_PROFILE)

    companion object {
        const val KEY_FEED_POST_ID = "feed_post_id"
        const val KEY_CONTENT_TEXT = "content_text"
        const val ROUTE_HOME = "home"
        const val ROUTE_COMMENTS = // "comments/{feed_post_id}/{feed_post_desc}"
                "${Comments.ROUTE_FOR_ARGS}/" +
                "{$KEY_FEED_POST_ID}/" +
                "{$KEY_CONTENT_TEXT}"
        const val ROUTE_NEWS_FEED = "news_feed"

        const val ROUTE_FAVOURITE = "favourite"
        const val ROUTE_PROFILE = "profile"
    }
}