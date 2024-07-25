package com.katorabian.compose_news.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.katorabian.compose_news.domain.FeedPostItem
import com.katorabian.compose_news.domain.NavigationItem

@Preview
@Composable
fun MainScreen() {
    val feedPost = remember { mutableStateOf(FeedPostItem()) }

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
    ) { it
        PostCard(
            modifier = Modifier.padding(8.dp),
            feedPost = feedPost.value,
            onStatisticItemClickListener = { newItem ->
                val oldStatistics = feedPost.value.statistics
                val newStatistics = oldStatistics.toMutableList().apply {
                    replaceAll { oldItem ->
                        if (oldItem.type == newItem.type) {
                            oldItem.copy(
                                count = oldItem.count + 1
                            )
                        } else {
                            oldItem
                        }
                    }
                }
                feedPost.value = feedPost.value.copy(
                    statistics = newStatistics
                )
            }
        )
    }
}