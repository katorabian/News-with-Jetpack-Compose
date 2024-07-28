package com.katorabian.compose_news.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.katorabian.compose_news.presentation.viewModel.PostViewModel
import com.katorabian.compose_news.domain.FeedPostItem
import com.katorabian.compose_news.domain.NavigationItem
import com.katorabian.compose_news.domain.StatisticType

@Composable
fun MainScreen(viewModel: PostViewModel) {
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                val selectedItemPos = remember { mutableStateOf(0) }
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemPos.value == index,
                        onClick = { selectedItemPos.value = index },
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
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
        val feedPost = viewModel.feedList.observeAsState(emptyList())
        LazyColumn(modifier = Modifier.padding(it)) {
            items(
                items = feedPost.value,
                key = { it.id }
            ) { post ->
                fun updateCount(type: StatisticType) = viewModel.updateCount(post, type)

                PostCard(
                    modifier = Modifier.padding(8.dp),
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