package com.katorabian.compose_news.presentation.screen.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.katorabian.compose_news.common.constant.ZERO_INT
import com.katorabian.compose_news.presentation.navigation.AppNavGraph
import com.katorabian.compose_news.presentation.navigation.rememberNavigationState
import com.katorabian.compose_news.presentation.screen.comments.CommentsScreen
import com.katorabian.compose_news.presentation.screen.main.models.BottomNavItem
import com.katorabian.compose_news.presentation.screen.news.NewsFeedScreen

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .height(58.dp)
                    .shadow(4.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

                val items = listOf(
                    BottomNavItem.Home,
                    BottomNavItem.Favorite,
                    BottomNavItem.Profile
                )
                items.forEach { item ->
                    val isSelected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false

                    NavigationBarItem(
                        modifier = Modifier.height(20.dp),
                        selected = isSelected,
                        onClick = {
                            if (!isSelected) {
                                navigationState.navigateTo(item.screen.route)
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = item.titleResId),
                                fontSize = 11.sp
                            )
                        },
                        colors = NavigationBarItemColors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            selectedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary,
                            disabledIconColor = Color.Green,
                            disabledTextColor = Color.Green
                        )
                    )
                }
            }

        }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            newsFeedScreenContent = {
                NewsFeedScreen(
                    paddingValues = paddingValues,
                    onCommentClickListener = { postItem ->
                        navigationState.navigateToComments(postItem)
                    }
                )
            },
            commentsScreenContent = { postItem ->
                CommentsScreen(
                    feedPost = postItem,
                    onNavigateUp = {
                        navigationState.navHostController.popBackStack()
                    }
                )
            },
            favoriteScreenContent = { TextCounter(name = "NavigationItem.Favorite") },
            profileScreenContent = { TextCounter(name = "NavigationItem.Profile") }
        )
    }
}

@Composable
fun TextCounter(name: String) {
    var clickCount by rememberSaveable { mutableStateOf(ZERO_INT) }
    Text(
        modifier = Modifier.clickable { clickCount++ },
        text = "$name Count: $clickCount",
        color = Color.Black
    )
}