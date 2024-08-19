package com.katorabian.compose_news.presentation.screen.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.katorabian.compose_news.R
import com.katorabian.compose_news.domain.model.FeedPostItem
import com.katorabian.compose_news.domain.model.PostCommentItem
import com.katorabian.compose_news.presentation.ComposeNewsApp
import com.katorabian.compose_news.presentation.theme.DarkBlue
import kotlin.random.Random

private val LOAD_MORE_KEY = Random.nextFloat()

@Composable
fun CommentsScreen(
    modifier: Modifier = Modifier,
    feedPost: FeedPostItem,
    onNavigateUp: () -> Unit
) {
    val component = (LocalContext.current.applicationContext as ComposeNewsApp)
        .component
        .getCommentsScreenComponentFactory()
        .create(feedPost)

    val viewModel: CommentsViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.commentsState.collectAsState(CommentsScreenState.Initial)

    Scaffold(
        modifier = modifier,
        topBar = {
            CommentsScreenHeader(
                postItem = feedPost,
                onNavigateUp = onNavigateUp
            )
        }
    ) {

        when (val currState = screenState.value) {

            CommentsScreenState.FirstLoad -> {
                SideEffect {
                    viewModel.triggerLoadComments()
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = DarkBlue)
                }
            }

            is CommentsScreenState.CommentsList -> {
                LazyColumn(
                    modifier = Modifier.padding(it),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 72.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (feedPost.contentText.isNotBlank()) {
                        item(key = feedPost.id) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = feedPost.contentText,
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                    items(
                        items = currState.comments,
                        key = { item -> item.id }
                    ) { commentItem ->
                        CommentItem(
                            commentItem = commentItem
                        )
                    }
                    item(key = LOAD_MORE_KEY) {
                        if (currState.isLoadMore) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = DarkBlue)
                            }
                        } else {
                            SideEffect {
                                viewModel.triggerLoadComments()
                            }
                        }
                    }
                }
            }
            CommentsScreenState.Initial -> {
                /* do nothing */
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreenHeader(
    postItem: FeedPostItem,
    onNavigateUp: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.shadow(4.dp),
        title = {
            Text(
                text = stringResource(R.string.comments_title),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 8.dp)
            )
        },
        navigationIcon = {
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
private fun CommentItem(
    modifier: Modifier = Modifier,
    commentItem: PostCommentItem
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            model = commentItem.authorAvatarUrl,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = commentItem.authorName,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                lineHeight = 17.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = commentItem.commentText,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                lineHeight = 17.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = commentItem.publicationDate,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSecondary,
                lineHeight = 17.sp
            )
        }
    }
}