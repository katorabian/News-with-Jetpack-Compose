package com.katorabian.compose_news.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.get

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    homeScreenContent: @Composable () -> Unit,
    favoriteScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.NewsFeed.route
    ) {
        navigation(
            startDestination = Screen.NewsFeed.route,
            route = Screen.Home.route
        ) {
            composable(route = Screen.NewsFeed.route) {
                homeScreenContent()
            }
            composable(route = Screen.Comments.route) {
                commentsScreenContent()
            }
        }
        composable(route = Screen.Favourite.route) {
            favoriteScreenContent()
        }
        composable(route = Screen.Profile.route) {
            profileScreenContent()
        }
    }
}