package com.katorabian.compose_news.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.katorabian.compose_news.domain.constant.EMPTY_STRING
import com.katorabian.compose_news.domain.constant.ZERO_INT
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
                navArgument(Screen.KEY_FEED_POST_ID) { type = NavType.IntType },
                navArgument(Screen.KEY_CONTENT_TEXT) { type = NavType.StringType }
            )
        ) { // "comments/{feed_post_id}"
            val feedPostId = it.arguments?.getInt(Screen.KEY_FEED_POST_ID)?: ZERO_INT
            val contentText = it.arguments?.getString(Screen.KEY_CONTENT_TEXT)?: EMPTY_STRING
            commentsScreenContent(FeedPostItem(id = feedPostId, contentText = contentText))
        }
    }
}