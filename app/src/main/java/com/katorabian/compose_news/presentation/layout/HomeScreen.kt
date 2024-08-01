package com.katorabian.compose_news.presentation.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.StatisticType
import com.katorabian.compose_news.presentation.model.HomeScreenState
import com.katorabian.compose_news.presentation.viewModel.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    val screenState = viewModel.screenState.observeAsState(HomeScreenState.Initial)
    when(val currentState = screenState.value) {
        is HomeScreenState.Posts -> {
            FeedPosts(
                viewModel = viewModel,
                paddingValues = paddingValues,
                posts = currentState.posts
            )
        }
        is HomeScreenState.Comments -> {
            CommentsScreen(postItem = currentState.feedPost, comments = currentState.comments)
        }
        HomeScreenState.Initial -> {
            /* do nothing */
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FeedPosts(
    viewModel: MainViewModel,
    paddingValues: PaddingValues,
    posts: List<FeedPostItem>
) {
    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }

    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            vertical = 16.dp,
            horizontal = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = posts,
            key = { item -> item.id }
        ) { post ->
            val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
                positionalThreshold = { screenWidth / 2 },
                confirmValueChange = { value: SwipeToDismissBoxValue ->
                    val isDismissed = value != SwipeToDismissBoxValue.Settled
                    if (isDismissed) {
                        viewModel.removePostItem(post)
                    }

                    return@rememberSwipeToDismissBoxState isDismissed
                }
            )

            fun updateCount(type: StatisticType) = viewModel.updateStatisticCount(post, type)

            SwipeToDismissBox(
                modifier = Modifier.animateItemPlacement(),
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