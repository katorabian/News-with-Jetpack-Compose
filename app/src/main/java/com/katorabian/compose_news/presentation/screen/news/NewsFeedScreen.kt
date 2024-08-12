package com.katorabian.compose_news.presentation.screen.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.StatisticType
import com.katorabian.compose_news.presentation.theme.DarkBlue

@Composable
fun NewsFeedScreen(
    paddingValues: PaddingValues,
    onCommentClickListener: (FeedPostItem) -> Unit
) {
    val viewModel: NewsFeedViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(NewsFeedScreenState.Initial)

    when(val currentState = screenState.value) {
        is NewsFeedScreenState.Posts -> {
            FeedPosts(
                viewModel = viewModel,
                paddingValues = paddingValues,
                posts = currentState.posts,
                onCommentClickListener = onCommentClickListener,
                nextDataIsLoading = currentState.nextDataIsLoading
            )
        }
        NewsFeedScreenState.Initial -> {
            /* do nothing */
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun FeedPosts(
    viewModel: NewsFeedViewModel,
    paddingValues: PaddingValues,
    posts: List<FeedPostItem>,
    nextDataIsLoading: Boolean,
    onCommentClickListener: (FeedPostItem) -> Unit
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

            SwipeToDismissBox(
                modifier = Modifier.animateItemPlacement(),
                state = swipeToDismissBoxState,
                backgroundContent = {},
                enableDismissFromEndToStart = true,
                enableDismissFromStartToEnd = false
            ) {
                PostCard(
                    feedPost = post,
                    onViewsClickListener = {
                        viewModel.updateStatisticCount(post, StatisticType.VIEWS)
                    },
                    onShareClickListener = {
                        viewModel.updateStatisticCount(post, StatisticType.SHARES)
                    },
                    onCommentClickListener = {
                        onCommentClickListener(post)
                    },
                    onLikeClickListener = {
                        viewModel.changeLikeStatus(post)
                    }
                )
            }
        }
        item {
            if (nextDataIsLoading) {
                Box(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = DarkBlue
                    )
                }
            } else {
                SideEffect {
                    viewModel.loadNextRecommendations()
                }
            }
        }
    }
}