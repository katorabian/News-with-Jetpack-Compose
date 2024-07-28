package com.katorabian.compose_news.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.katorabian.compose_news.presentation.viewModel.PostViewModel
import com.katorabian.compose_news.domain.NavigationItem
import com.katorabian.compose_news.domain.StatisticType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: PostViewModel) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.height(58.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                val selectedItemPos = remember { mutableStateOf(0) }
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        modifier = Modifier.height(20.dp),
                        selected = selectedItemPos.value == index,
                        onClick = { selectedItemPos.value = index },
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
    ) {
        val feedPosts = viewModel.feedPosts.observeAsState(emptyList())
        val screenWidth = with(LocalDensity.current) {
            LocalConfiguration.current.screenWidthDp.dp.toPx()
        }

        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(
                vertical = 16.dp,
                horizontal = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = feedPosts.value,
                key = { item -> item.id }
            ) { post ->
                val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
                    positionalThreshold = { screenWidth / 2 },
                    confirmValueChange = { value: SwipeToDismissBoxValue ->
                        val isDismissed = value != SwipeToDismissBoxValue.Settled
                        if (isDismissed) {
                            viewModel.removeItem(post)
                        }

                        return@rememberSwipeToDismissBoxState isDismissed
                    }
                )

                fun updateCount(type: StatisticType) = viewModel.updateCount(post, type)

                SwipeToDismissBox(
                    state = swipeToDismissBoxState,
                    backgroundContent = {},
                    enableDismissFromEndToStart = true,
                    enableDismissFromStartToEnd = false
                ) {
                    PostCard(
                        feedPost = post,
                        onViewsClickListener = { updateCount(StatisticType.VIEWS) },
                        onShareClickListener = { updateCount(StatisticType.SHARES) },
                        onCommentClickListener = { updateCount(StatisticType.COMMENTS) },
                        onLikeClickListener = { updateCount(StatisticType.LIKES) }
                    )
                }
            }
        }
    }
}