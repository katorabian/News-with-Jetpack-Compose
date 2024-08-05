package com.katorabian.compose_news.presentation.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.katorabian.compose_news.domain.model.FeedPostItem

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPostItem) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(route = Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(Screen.KEY_FEED_POST) {
                    type = FeedPostItem.NavigationType
                }
            )
        ) { // "comments/{feed_post_json}"
            val args = it.arguments
            val feedPost = kotlin.runCatching { //null safety
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    args?.getParcelable(Screen.KEY_FEED_POST)
                } else {
                    args?.getParcelable(
                        Screen.KEY_FEED_POST,
                        FeedPostItem::class.java
                    )
                }
            }.getOrNull()
                ?: throw ClassCastException("Args is null")
            commentsScreenContent(feedPost)
        }
    }
}