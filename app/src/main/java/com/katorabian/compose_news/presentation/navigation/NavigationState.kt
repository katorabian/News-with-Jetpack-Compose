package com.katorabian.compose_news.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.katorabian.compose_news.domain.model.FeedPostItem

class NavigationState(
    val navHostController: NavHostController
) {

    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            popUpTo(navHostController.graph.findStartDestination().id) { // remove screens between
                saveState = true // save state of removed screens
            }
            launchSingleTop = true // do not make new copy of already exits screen
            restoreState = true // restore screen state if exist
        }
    }

    fun navigateToComments(postItem: FeedPostItem) {
        // prevent drop of this screen from inner graph backStack, when graph calls again
        // (opens on this screen)
        navHostController.navigate(Screen.Comments.getRouteWithArgs(postItem))
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}