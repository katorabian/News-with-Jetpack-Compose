package com.katorabian.compose_news.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.katorabian.compose_news.domain.constant.EMPTY_STRING
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
                    type = NavType.StringType
                }
            )
        ) { // "comments/{feed_post_json}"
            val feedPostJson = it.arguments?.getString(Screen.KEY_FEED_POST)?: EMPTY_STRING
            val feedPost = Gson().fromJson(feedPostJson, FeedPostItem::class.java)
            commentsScreenContent(feedPost)
        }
    }
}